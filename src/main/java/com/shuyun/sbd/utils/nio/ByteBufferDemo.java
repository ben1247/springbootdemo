package com.shuyun.sbd.utils.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * Component: 主要通过读取文件内容，写到ByteBuffer里，然后再从ByteBuffer对象中获取数据，显示到控制台
 * Description:
 * Date: 16/7/21
 *
 * @author yue.zhang
 */
public class ByteBufferDemo {

    public static void readFile(String fileName) {

        RandomAccessFile randomAccessFile = null;
        FileChannel fileChannel = null;

        try{

            randomAccessFile = new RandomAccessFile(fileName,"rw");

            fileChannel = randomAccessFile.getChannel();

            ByteBuffer byteBuffer = ByteBuffer.allocate(6);

            int size = fileChannel.read(byteBuffer);
            while (size > 0){
                // 把ByteBuffer从写模式，转变成读取模式
                byteBuffer.flip();

                // 定义一个字符集为utf-8
                Charset charset = Charset.forName("UTF-8");
                System.out.println(charset.newDecoder().decode(byteBuffer).toString());

                byteBuffer.clear();

                size = fileChannel.read(byteBuffer);
            }


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                assert fileChannel != null;
                fileChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                randomAccessFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
