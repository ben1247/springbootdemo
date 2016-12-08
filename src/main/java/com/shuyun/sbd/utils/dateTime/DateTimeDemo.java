package com.shuyun.sbd.utils.dateTime;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

/**
 * Component:
 * Description:
 * Date: 16/12/7
 *
 * @author yue.zhang
 */
public class DateTimeDemo {

    public static void testClock(){
        final Clock clock = Clock.systemUTC();
        System.out.println(clock.instant());
        System.out.println(clock.millis());
    }

    public static void testDuration(){
        final LocalDateTime from = LocalDateTime.of( 2014, Month.APRIL, 16, 0, 0, 0 );
        final LocalDateTime to = LocalDateTime.of( 2015, Month.APRIL, 16, 23, 59, 59 );

        Duration duration = Duration.between(from,to);
        System.out.println("days: " + duration.toDays());
        System.out.println("hours: " + duration.toHours());
    }

    public static void main(String [] args){
        testDuration();
    }


}
