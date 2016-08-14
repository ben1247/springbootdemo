package com.shuyun.sbd.utils.netty.protobuf.user;

import com.shuyun.sbd.utils.netty.protobuf.EmailProbuf;
import com.shuyun.sbd.utils.netty.protobuf.ResponseMsgProbuf;
import com.shuyun.sbd.utils.netty.protobuf.UserProbuf;
import com.shuyun.sbd.utils.netty.media.Remote;
import org.springframework.stereotype.Controller;

/**
 * Component:
 * Description:
 * Date: 16/8/7
 *
 * @author yue.zhang
 */
@Controller
public class UserController {

    @Remote("saveUser")
    public ResponseMsgProbuf.ResponseMsg saveUser(UserProbuf.User user){
        UserProbuf.User.Builder newUser = UserProbuf.User.newBuilder().setPhone(user.getPhone());
        newUser.setId(user.getId());
        newUser.setUsername(user.getUsername());

        // 业务处理
        ResponseMsgProbuf.ResponseMsg reponse = ResponseMsgProbuf.ResponseMsg.newBuilder().setRespnoseParam(newUser.build().toByteString()).build();
        return reponse;
    }

    @Remote("getEmailByUser")
    public ResponseMsgProbuf.ResponseMsg getEmailByUser(UserProbuf.User user){
        EmailProbuf.Email.Builder email = EmailProbuf.Email.newBuilder()
                                            .setContent("content_test")
                                            .setFromUser("zhangsan")
                                            .setId(1).setSubject("subject_test");

        ResponseMsgProbuf.ResponseMsg response = ResponseMsgProbuf.ResponseMsg.newBuilder().setRespnoseParam(email.build().toByteString()).build();
        return response;
    }

    @Remote("httpGetEmailByUser")
    public Object getEmail(String email){
        email = email + " hhhh";
        return email;
    }
}
