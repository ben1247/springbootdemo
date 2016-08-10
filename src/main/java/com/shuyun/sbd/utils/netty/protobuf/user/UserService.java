package com.shuyun.sbd.utils.netty.protobuf.user;

import com.shuyun.sbd.utils.netty.protobuf.PbClient;
import com.shuyun.sbd.utils.netty.protobuf.RequestMsgProbuf.RequestMsg;
import com.shuyun.sbd.utils.netty.protobuf.UserProbuf.User;
import com.shuyun.sbd.utils.netty.protobuf.EmailProbuf.Email;

/**
 * Component:
 * Description:
 * Date: 16/8/4
 *
 * @author yue.zhang
 */
public class UserService {

    // 请在 UserServiceTest 这个测试类中运行
    public User saveUser() throws Exception {
        User.Builder user = User.newBuilder();
        user.setId(1);
        user.setPhone("13918641233");
        user.setUsername("张三");

        RequestMsg.Builder requestMsg = RequestMsg.newBuilder();
        requestMsg.setCmd("saveUser");
        requestMsg.setRequestParam(user.build().toByteString());

        // 调用netty客户端进行通讯
        return User.parseFrom(PbClient.start(requestMsg));
    }

    public Email getEmail() throws Exception {
        User.Builder user = User.newBuilder();
        user.setId(1);
        user.setPhone("13918641233");
        user.setUsername("张三");

        RequestMsg.Builder requestMsg = RequestMsg.newBuilder();
        requestMsg.setCmd("getEmailByUser");
        requestMsg.setRequestParam(user.build().toByteString());

        // 调用netty客户端进行通讯
        return Email.parseFrom(PbClient.start(requestMsg));
    }

}
