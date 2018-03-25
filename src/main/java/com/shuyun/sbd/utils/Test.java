package com.shuyun.sbd.utils;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.security.MessageDigest;
import java.text.NumberFormat;
import java.util.*;

/**
 * Component:
 * Description:
 * Date: 16/7/30
 *
 * @author yue.zhang
 */
public class Test {

    private final static String NODE_NAME = "n_";

    private static String getNodeNumber(String str, String nodeName) {
        int index = str.lastIndexOf(nodeName);
        if (index >= 0) {
            index += NODE_NAME.length();
            return index <= str.length() ? str.substring(index) : "";
        }
        return str;
    }

    /**
     * 位运算
     */
    public static void bitOperation() {
        String a1 = "aaa";
        String a2 = "aaa";
        System.out.println(a1.hashCode());
        System.out.println(a2.hashCode());

        int number = 10;

        System.out.println(number);

        number = number << 1; // 10 * 2 = 20

        number = number << 4; // 20 * 2 * 2 * 2 * 2 = 320

        System.out.println(number);

        number = 10;

        number = number >> 1;  // 10 / 2 = 5

        System.out.println(number);

        number = 10;

        number = number >>> 1;

        System.out.println(number);

        int number1 = 10;
        int number2 = 10;

        number1 = number1 << 1;
        number2 <<= 1;
        System.out.println(number1);
        System.out.println(number2);


        HashMap<String, Integer> testHashMap = new HashMap<>();
        System.out.println(testHashMap.put("a", 1)); // 应该返回oldValue
        System.out.println(testHashMap.put("a", 2));

        int a = 5 & 6; // 将5和6转化成二进制，然后进行与运算，只要有一个为0 则为0

        System.out.println(a);

        System.out.println(5 / 2);

        int num = 1_2_3;
        System.out.println(num);

        newswitch("me");

        System.out.println(1 << 16);

        int ssize = 1;
        ssize <<= 1;
        System.out.println(ssize);


        int x = 100;
        int y = 200;
        int average = (x & y) + ((x ^ y) >> 1);

        x ^= y;
        y ^= x;
        x ^= y;

        System.out.println(average);

        System.out.println("x = " + x);
        System.out.println("y = " + y);

        int code = "abc".hashCode();

        char c = '\u0080';

        // 延迟超时
//        int sleepMs = 1000 * Math.max(1, new Random().nextInt(1 << (3 + 1)));
//        System.out.println(sleepMs);

//        String str = "abcden_0001";
//        String nodeName = NODE_NAME;
//
//        System.out.println(getNodeNumber(str,nodeName));
    }

    static void IOUtilsDemo() throws IOException {
        InputStream inputStream = new ByteArrayInputStream("你好\n哈哈\n你针的好慢".getBytes());
//        String res = IOUtils.toString(inputStream,"utf-8");
//        System.out.println(res);

//        InputStream inputStream = new URL("http://wx.qlogo.cn/mmopen/JbMzLh0FoAicfKh4doOwoLHWl9nibThIiaFsVTtCdNhba9oVCvDGzjXdQE1mSia5ickVlNJ2HtXW4Xp38BmRzTZib69u0tQ7Fjgicak/0").openConnection().getInputStream();

        List<String> lines = IOUtils.readLines(inputStream, "utf-8");

        lines.forEach(System.out::println);
    }

    static int indexFor(int h, int length) {
        return h & (length - 1);
    }

    static void newswitch(String str) {
        switch (str) {
            case "me":
                System.out.println("me");
                break;
            default:
                System.out.println("other");
                break;
        }
    }

    static void addStringToIntList() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<Integer> intList = new ArrayList<>();
        Method addMethod = intList.getClass().getMethod("add", Object.class);
        addMethod.invoke(intList, "java反射机制实例");
        System.out.println(intList.get(0));
    }

    static void updateArray() {
        int[] temp = {1, 2, 3, 4, 5};
        Class<?> componentClass = temp.getClass().getComponentType();
        System.out.println("数组类型：" + componentClass.getName());

    }


    public static void main(String[] args) throws Exception {
        for(int i = 0 ; i < 100000 ; i++){
            System.out.println(getOrderIdByUUId());
        }
    }

    static void Percent() {
        //获取格式化对象
        NumberFormat nt = NumberFormat.getPercentInstance();
        //设置百分数精确度2即保留两位小数
        nt.setMinimumFractionDigits(3);

        double percent = 30D / 103D;
        String str = nt.format(percent);

        System.out.println(str);
    }

    static void f(String s, ArrayList<String> l) {
        if (s.isEmpty()) {
            for (int i = 0; i < l.size(); i++) {
                System.out.println(s + l.get(i));
                if (i == l.size() - 1) return;
                f(s + l.get(i), l);
            }
        } else {
            char[] c = new char[1];
            c[0] = s.charAt(s.length() - 1);
            String str = new String(c);
            for (int i = l.lastIndexOf(str); i < l.size(); i++) {
                System.out.println(s + l.get(i + 1));
                if (i == l.size() - 2) return;
                f(s + l.get(i + 1), l);
            }
        }
    }

    /**
     * 二分查找
     */
    static void erFen() {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int num = 1;
        int low = 0;
        int high = array.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (num > array[mid]) {
                low = mid + 1;
            } else if (num < array[mid]) {
                high = mid - 1;
            } else {
                System.out.println(mid);
                return;
            }
        }

        System.out.println(-1);

    }

    static void hash(Integer key, Integer size) throws InterruptedException {
        int h ;
        int hash = (h = key.hashCode()) ^ (h >>> 16);

        // 对hash值取模，将hash值路由到指定的内存队列中，比如内存队列大小8
        // 用内存队列的数量对hash值取模之后，结果一定是在0~7之间
        // 所以任何一个商品id都会被固定路由到同样的一个内存队列中去的
        int index = (size - 1) & hash;

        System.out.println(index);

    }

    public static String getOrderIdByUUId() {
        int machineId = 1;//最大支持1-9个集群机器部署
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型
//        return machineId + String.format("%012d", hashCodeV);
        return String.valueOf(hashCodeV);
    }

}
