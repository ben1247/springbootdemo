package com.shuyun.sbd.utils.netty.http.file;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.SystemPropertyUtil;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpResponseStatus.*;

/**
 * Component:
 * Description:
 * Date: 16/8/20
 *
 * @author yue.zhang
 */
public class HttpFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final String HTTP_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz";

    private static final String HTTP_DATE_GMT_TIMEZONE = "GMT";

    private static final int HTTP_CACHE_SECONDS = 60;

    private static final Pattern INSECURE_URI = Pattern.compile(".*[<>&\"].*");

    private static final Pattern ALLOWED_FILE_NAME = Pattern.compile("[A-Za-z0-9][-_A-Za-z0-9\\.]*");

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {

        // 监测解码情况
        if (!request.getDecoderResult().isSuccess()) {
            sendError(ctx,BAD_REQUEST);
            return;
        }
        final String uri = request.getUri();
        final String path = sanitizeUri(uri);
        if(path == null){
            sendError(ctx,FORBIDDEN);
            return;
        }
        //读取要下载的文件
        File file = new File(path);
        if(file.isHidden() || !file.exists()){
            sendError(ctx,NOT_FOUND);
            return;
        }
        if (file.isDirectory()) {
            if (uri.endsWith("/")) {
                sendListing(ctx, file);
            } else {
                sendRedirect(ctx, uri + '/');
            }
            return;
        }

        if(!file.isFile()){
            sendError(ctx,FORBIDDEN);
            return;
        }

        // Cache Validation
//        String ifModifiedSince = request.headers().get(IF_MODIFIED_SINCE);
//        if(ifModifiedSince != null && !ifModifiedSince.isEmpty()){
//            SimpleDateFormat dateFormatter = new SimpleDateFormat(HTTP_DATE_FORMAT, Locale.US);
//            Date ifModifiedSinceDate = dateFormatter.parse(ifModifiedSince);
//            // Only compare up to the second because the datetime format we send to the client does not have milliseconds
//            long ifModifiedSinceDateSeconds = ifModifiedSinceDate.getTime() / 1000;
//            long fileLastModifiedSeconds = file.lastModified() / 1000;
//            if (ifModifiedSinceDateSeconds == fileLastModifiedSeconds) {
//                sendNotModified(ctx);
//                return;
//            }
//        }

        RandomAccessFile raf;
        try{
            raf = new RandomAccessFile(file,"r"); // 以只读得方式打开文件
        }catch (FileNotFoundException e){
            sendError(ctx,NOT_FOUND);
            return;
        }
        long fileLength = raf.length();
        HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1,OK);
        HttpHeaders.setContentLength(response, fileLength);
        setContentTypeHeader(response, file);
        setDateAndCacheHeaders(response, file);
        if(HttpHeaders.isKeepAlive(request)){
            response.headers().set("CONNECTION",HttpHeaders.Values.KEEP_ALIVE);
        }

        // Write the initial line and the header
        ctx.write(response);

        // Write the content
        ChannelFuture sendFileFuture;
        if(ctx.pipeline().get(SslHandler.class) == null){
            sendFileFuture = ctx.write(new DefaultFileRegion(raf.getChannel(),0,fileLength),ctx.newProgressivePromise());
        }else {
            // 通过netty的chunkedFile对象直接将文件写入到发送缓冲区中。
            sendFileFuture = ctx.write(new ChunkedFile(raf,0,fileLength,8192),ctx.newProgressivePromise());
        }
        // 增加监听，如果发送完成，打印 Transfer complete
        sendFileFuture.addListener(new ChannelProgressiveFutureListener() {
            @Override
            public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) throws Exception {
                if(total < 0){
                    System.err.println(future.channel() + " Transfer progress: " + progress);
                }else {
                    System.err.println(future.channel() + " Transfer progress: " + progress + " / " + total);
                }
            }

            @Override
            public void operationComplete(ChannelProgressiveFuture future) throws Exception {
                System.err.println(future.channel() + " Transfer complete.");
            }
        });

        // 如果使用chunked编码，最后需要发送一个编码结束的空消息体，将LastHttpContent的EMPTY_LAST_CONTEN发送到缓冲区中，
        // 标识所有消息体已经发送完成，同时调用flush方法将之前在发送缓冲区的消息刷新到SocketChannel中发送给对方。
        ChannelFuture lastContentFuture = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);

        // Decide whether to close the connection or not
        if(!HttpHeaders.isKeepAlive(request)){
            // Close the connection when the whole content is written out.
            lastContentFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        if(ctx.channel().isActive()){
            sendError(ctx,INTERNAL_SERVER_ERROR);
        }
    }

    private static String sanitizeUri(String uri){
        // Decode the path
        try {
            uri = URLDecoder.decode(uri,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            try{
                uri = URLDecoder.decode(uri,"ISO-8859-1");
            }catch (UnsupportedEncodingException e1) {
                throw new Error(e);
            }
        }

        if(!uri.startsWith("/")){
            return null;
        }

        // Convert file separators
        uri = uri.replace('/',File.separatorChar);

        // Simplistic dumb security check.
        // You will have to do something serious in the production environment.
        if (uri.contains(File.separator + '.')
                || uri.contains('.' + File.separator)
                || uri.startsWith(".")
                || uri.endsWith(".")
                || INSECURE_URI.matcher(uri).matches()) {
            return null;
        }

        // Convert to absolute path.
        return SystemPropertyUtil.get("user.dir") + File.separator + uri;

    }


    private static void sendListing(ChannelHandlerContext ctx , File dir){
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,OK);
        response.headers().set(CONTENT_TYPE,"text/html;charset=UTF-8");

        StringBuilder buf = new StringBuilder();
        String dirPath = dir.getPath();

        buf.append("<!DOCTYPE html>\r\n");
        buf.append("<html><head><title>");
        buf.append("Listing of: ");
        buf.append(dirPath);
        buf.append("</title></head><body>\r\n");

        buf.append("<h3>Listing of: ");
        buf.append(dirPath);
        buf.append("</h3>\r\n");

        buf.append("<ul>");
        buf.append("<li><a href=\"../\">..</a></li>\r\n");

        for(File f : dir.listFiles()){
            if(f.isHidden() || !f.canRead()){
                continue;
            }
            String name = f.getName();
            if (!ALLOWED_FILE_NAME.matcher(name).matches()){
                continue;
            }
            buf.append("<li><a href=\"");
            buf.append(name);
            buf.append("\">");
            buf.append(name);
            buf.append("</a></li>\r\n");
        }

        buf.append("</ul></body></html>\r\n");

        ByteBuf buffer = Unpooled.copiedBuffer(buf, CharsetUtil.UTF_8);

        response.content().writeBytes(buffer);

        buffer.release();

        // Close the connection as soon as the error message is sent.
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);

    }

    private static void sendRedirect(ChannelHandlerContext ctx , String newUri){
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,FOUND);
        response.headers().set(LOCATION,newUri);

        // Close the connection as soon as the error message is sent
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private static void sendError(ChannelHandlerContext ctx , HttpResponseStatus status){
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,status,
                Unpooled.copiedBuffer("Failure: " + status + "\r\n", CharsetUtil.UTF_8));
        response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");

        // Close the connection as soon as the error message is sent.
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * when file timestamp is the same as what the browser is sending up , send a "304 Not Modified"
     * @param ctx
     */
    private static void sendNotModified(ChannelHandlerContext ctx){
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,NOT_MODIFIED);

        setDateHeader(response);

        // Close the connection as soon as the error message is sent.
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * sets the Date header for the http response
     * @param response
     */
    private static void setDateHeader(FullHttpResponse response){
        SimpleDateFormat dateFormatter = new SimpleDateFormat(HTTP_DATE_FORMAT,Locale.US);
        dateFormatter.setTimeZone(TimeZone.getTimeZone(HTTP_DATE_GMT_TIMEZONE));

        Calendar time = new GregorianCalendar();
        response.headers().set(DATE, dateFormatter.format(time.getTime()));
    }

    /**
     * Sets the Date and Cache headers for the HTTP Response
     * @param response
     * @param fileToCache  file to extract content type
     */
    private static void setDateAndCacheHeaders(HttpResponse response , File fileToCache){
        SimpleDateFormat dateFormatter = new SimpleDateFormat(HTTP_DATE_FORMAT,Locale.US);
        dateFormatter.setTimeZone(TimeZone.getTimeZone(HTTP_DATE_GMT_TIMEZONE));
        // Date header
        Calendar time = new GregorianCalendar();
        response.headers().set(DATE, dateFormatter.format(time.getTime()));

        // Add cache headers
        time.add(Calendar.SECOND,HTTP_CACHE_SECONDS);
        response.headers().set(EXPIRES, dateFormatter.format(time.getTime()));
        response.headers().set(CACHE_CONTROL, "private, max-age=" + HTTP_CACHE_SECONDS);
        response.headers().set(LAST_MODIFIED,dateFormatter.format(new Date(fileToCache.lastModified())));
    }

    /**
     * Sets the content type header for the HTTP Response
     *
     * @param response
     * @param file file to extract content type
     */
    private static void setContentTypeHeader(HttpResponse response , File file){
        MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
        response.headers().set(CONTENT_TYPE,mimeTypesMap.getContentType(file.getPath()));
    }


}
