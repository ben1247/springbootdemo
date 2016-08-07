package com.shuyun.sbd.utils.netty.protobuf.user;

import com.shuyun.sbd.utils.netty.protobuf.UserProbuf;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Component:
 * Description:
 * Date: 16/8/7
 *
 * @author yue.zhang
 */
public class UserServiceTest extends TestCase {

    @Test
    public void testSave() throws Exception {
        UserService userService = new UserService();
        UserProbuf.User user = userService.save();
        System.out.println(user.getPhone());
    }
}