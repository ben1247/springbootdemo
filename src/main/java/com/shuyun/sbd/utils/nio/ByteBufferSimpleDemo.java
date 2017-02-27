package com.shuyun.sbd.utils.nio;

import java.nio.ByteBuffer;

/**
 * Component:
 * Description:
 * Date: 17/2/10
 *
 * @author yue.zhang
 */
public class ByteBufferSimpleDemo {

    public static void testMarkAndReset(){

        ByteBuffer b = ByteBuffer.allocate(15);
        for(int i = 0 ; i < 10 ; i++){
            b.put((byte) i);
        }

        b.flip(); // 准备读

        for(int i = 0 ; i < b.limit(); i++){
            System.out.print(b.get());
            if(i == 4){
                b.mark();
                System.out.print(" (mark at " + i + ") ");
            }
        }

        b.reset(); // 回到mark位置，并处理后续数据
        System.out.println("\nreset to mark");
        while (b.hasRemaining()){ // 后续所有数据都将处理
            System.out.print(b.get());
        }
        System.out.println();

    }

    public static void main(String [] args){
        testMarkAndReset();
    }

}
