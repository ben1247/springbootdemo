package com.shuyun.sbd.utils.netty.time.pojo;

import java.util.Date;

/**
 * Component:
 * Description:
 * Date: 16/7/30
 *
 * @author yue.zhang
 */
public class UnixTime {

    private final int value;


    public UnixTime() {

        this((int) (System.currentTimeMillis() / 1000L + 2208988800L));
    }


    public UnixTime(int value) {


        this.value = value;

    }

    public int value() {
        return value;
    }

    @Override
    public String toString() {
        return new Date((value() - 2208988800L) * 1000L).toString();
    }


}
