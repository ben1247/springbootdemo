package com.shuyun.sbd.utils.netty.http;

import com.shuyun.sbd.utils.JsonUtil;
import com.shuyun.sbd.utils.netty.http.bean.RequestParam;
import com.shuyun.sbd.utils.netty.protobuf.user.Person;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Component:
 * Description:
 * Date: 16/8/13
 *
 * @author yue.zhang
 */
public class HttpClientTest extends TestCase {

    @Test
    public void testHttpNettyClient() throws Exception {
        RequestParam requestParam = new RequestParam();
        requestParam.setCommand("httpGetEmailByUser");

        Person person = new Person();
        person.setId("12");
        person.setUsername("zhangsan");

        requestParam.setParameter(JsonUtil.writeValueAsString(person));
        Object response = HttpClient.start(requestParam);
        System.out.println(response.toString());
    }

}