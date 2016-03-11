package com.shuyun.sbd.demo.jvm.script;

/**
 * Component: Bytes 数组处理工具
 * Description:
 * Date: 16/3/9
 *
 * @author yue.zhang
 */
public class ByteUtils {

    public static int bytes2Int(byte[] b, int start , int len){
        int sum = 0;
        int end = start + len;
        for(int i = start; i < end; i++){
            int n = ((int)b[i]) & 0xff; /* 是按位或运算符 & 是按位与运算符 ^ 是按位异或运算符
                                           把number转换为二进制，只取最低的8位（bit）。因为0xff二进制就是1111 1111。
                                           & 运算是，如果对应的两个bit都是1，则那个bit结果为1，否则为0.
                                           比如 1010 & 1101 = 1000 （二进制）
                                           由于0xff最低的8位是1，因此number中低8位中的&之后，如果原来是1，结果还是1，原来是0，结果位还是0.
                                           高于8位的，0xff都是0，所以无论是0还是1，结果都是0。*/

            n <<= (--len) * 8;  /* 是复合运算符 a=a+3 可以写成a+=3
                                   类似的还有*=，%=，-=，/=...
                                   a=a<<2 就可以写成 a<<=2
                                   <<是位运算符里的左移 a对应内存里的存储的二进制码向左移2位
                                   如：a=4 0000 0100左移2位 0001 0000 （左端移出的不要，右端补0，左移一次相当于*2）*/
            sum = n + sum;
        }
        return sum;
    }

    public static byte[] int2Bytes(int value , int len){
        byte [] b = new byte[len];
        for(int i = 0 ; i < len ; i++){
            b[len-i-1] = (byte)((value >> 8 * i) & 0xff);
        }
        return b;
    }

    public static String bytes2String(byte[]b , int start , int len){
        return new String(b,start,len);
    }

    public static byte[] string2Bytes(String str){
        return str.getBytes();
    }

    public static byte[] bytesReplace(byte[] originalBytes,int offset , int len , byte [] replaceBytes){
        byte [] newBytes = new byte[originalBytes.length + (replaceBytes.length - len)];
        System.arraycopy(originalBytes,0,newBytes,0,offset);
        System.arraycopy(replaceBytes,0,newBytes,offset,replaceBytes.length);
        System.arraycopy(originalBytes,offset+len,newBytes,offset + replaceBytes.length,originalBytes.length-offset-len);
        return newBytes;
    }

}
