package com.shuyun.sbd.utils.jvm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Component:
 * Description:
 * Date: 16/10/2
 *
 * @author yue.zhang
 */
public class DrawApp {

    public static void main(String [] args){

        Point a = new Point(0,0);
        Point b = new Point(1,1);
        Point c = new Point(5,3);
        Point d = new Point(9,8);
        Point e = new Point(6,7);
        Point f = new Point(3,9);
        Point g = new Point(4,8);

        Line aLine = new Line(a,b);
        Line bLine = new Line(a,c);
        Line cLine = new Line(d,e);
        Line dLine = new Line(f,g);

        a = null;
        b = null;
        c = null;
        d = null;
        e = null;

        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

}
