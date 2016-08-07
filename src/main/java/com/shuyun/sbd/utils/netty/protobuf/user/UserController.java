package com.shuyun.sbd.utils.netty.protobuf.user;

import com.shuyun.sbd.utils.netty.protobuf.UserProbuf;
import com.shuyun.sbd.utils.netty.protobuf.media.Remote;
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
    public Object saveUser(UserProbuf.User user){
        UserProbuf.User.Builder newUser = UserProbuf.User.newBuilder().setPhone("13918648199");
        newUser.setId(1);
        newUser.setUsername("zhangsan");
        return newUser.build();
    }

}
