package com.shuyun.sbd.utils.netty.media;

import com.google.protobuf.ByteString;
import com.shuyun.sbd.utils.JsonUtil;
import com.shuyun.sbd.utils.netty.http.bean.RequestParam;
import com.shuyun.sbd.utils.netty.protobuf.RequestMsgProbuf;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import static io.netty.handler.codec.http.HttpHeaders.Names.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Component:
 * Description:
 * Date: 16/8/7
 *
 * @author yue.zhang
 */
public class Media {

    public static Map<String,MethodBean> methodBeans = new HashMap<>();

    public static Object execute(Object obj){

        Object response = null;

        String cmd = "";

        Object parameterObj = null;

        RequestParam httpRequestParam = null;

        RequestParam jsonParam = null;

        try {

            if(obj instanceof RequestMsgProbuf.RequestMsg){ // socket 请求
                cmd = ((RequestMsgProbuf.RequestMsg)obj).getCmd();
            }else if(obj instanceof HttpRequest){ // http 请求
                FullHttpRequest request = (FullHttpRequest)obj;

                ByteBuf buf = request.content();

                String req = buf.toString(Charset.forName("UTF-8"));

                httpRequestParam = JsonUtil.readValue(req, RequestParam.class);

                cmd = httpRequestParam.getCommand();
            }else if(obj instanceof String){
                jsonParam = JsonUtil.readValue(obj.toString(),RequestParam.class);
                cmd = jsonParam.getCommand();
            }

            MethodBean methodBean = methodBeans.get(cmd);

            Method method = methodBean.getMethod();

            Object bean = methodBean.getBean();

            // 获取目标方法参数类型
            Class parameterType = method.getParameterTypes()[0];

            if(obj instanceof RequestMsgProbuf.RequestMsg){

                // 获取目标方法参数类型的所有构造方法
                Constructor<?>[] constructors = parameterType.getDeclaredConstructors();

                Constructor c = null;
                for(Constructor constructor : constructors){
                    if(constructor.getParameterTypes()[0].getName().equals("boolean")){
                        c = constructor;
                    }
                }

                if(c != null){
                    c.setAccessible(true);

                    // 初始化参数
                    parameterObj = c.newInstance(true);

                    ByteString requestParam = ((RequestMsgProbuf.RequestMsg) obj).getRequestParam();

                    Method parameterMethod = parameterType.getMethod("parseFrom",ByteString.class);

                    // 把方法参数赋值
                    parameterObj = parameterMethod.invoke(parameterObj,requestParam);

                }

            }else if(httpRequestParam != null){
                if(method.getParameterTypes()[0].getName().equalsIgnoreCase("java.lang.String")){
                    parameterObj = httpRequestParam.getParameter().toString();
                }else{
                    parameterObj = JsonUtil.readValue(httpRequestParam.getParameter().toString(),method.getParameterTypes()[0]);
                }
            }else if(jsonParam != null){
                parameterObj = JsonUtil.readValue(httpRequestParam.getParameter().toString(),parameterType);
            }

            // 例：调用controller 的 saveUser 方法
            response = method.invoke(bean,parameterObj);

            if(obj instanceof HttpRequest){
                // 将请求序列化成json对象
                String jsonp = JsonUtil.writeValueAsString(response);
                FullHttpResponse httpResponse = new DefaultFullHttpResponse(
                        HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(jsonp.getBytes("UTF-8")));
                httpResponse.headers().set(CONTENT_TYPE,"text/plain");
                httpResponse.headers().set(CONTENT_LENGTH, httpResponse.content().readableBytes());
                httpResponse.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
                return httpResponse;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return response;
    }

}
