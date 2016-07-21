package com.shuyun.sbd.utils.nio;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * Component:
 * Description:
 * Date: 16/7/21
 *
 * @author yue.zhang
 */
public class ByteBufferDemoTest extends TestCase {

    @Test
    public void testReadFile() throws Exception {
        ByteBufferDemo.readFile("file/1.txt");
    }
}