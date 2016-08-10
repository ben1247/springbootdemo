package com.shuyun.sbd.utils.netty.protobuf.user;

import com.shuyun.sbd.utils.netty.protobuf.EmailProbuf;
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

    /**
     * 测试用户保存后使用netty＋protobuf 进行通讯，利用反射调用服务端的UserController.saveUser 方法，最后返回数据
     * @throws Exception
     */
    @Test
    public void testSaveUser() throws Exception {
        UserService userService = new UserService();
        UserProbuf.User user = userService.saveUser();
        System.out.println(user.getPhone());
    }

    @Test
    public void testGetEmail() throws Exception {
        UserService userService = new UserService();
        EmailProbuf.Email email = userService.getEmail();
        System.out.println(email.getContent());
    }
}