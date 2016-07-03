package com.shuyun.sbd.exception;

/**
 * Component:
 * Description:
 * Date: 16/6/5
 *
 * @author yue.zhang
 */
public class RepeatKillException extends RuntimeException {

    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
