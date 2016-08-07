package com.shuyun.sbd.utils.netty.protobuf.user;

import com.shuyun.sbd.utils.netty.protobuf.PbClient;
import com.shuyun.sbd.utils.netty.protobuf.RequestMsgProbuf.RequestMsg;
import com.shuyun.sbd.utils.netty.protobuf.UserProbuf.User;

/**
 * Component:
 * Description:
 * Date: 16/8/4
 *
 * @author yue.zhang
 */
public class UserService {

    // 请在 UserServiceTest 这个测试类中运行
    public User save() throws Exception {
        User.Builder user = User.newBuilder();
        user.setId(1);
        user.setPhone("13918641233");
        user.setUsername("张三");

        RequestMsg.Builder requestMsg = RequestMsg.newBuilder();
        requestMsg.setCmd("saveUser");
        requestMsg.setRequestParam(user.build().toByteString());

        return (User)PbClient.start(requestMsg);
    }

}
