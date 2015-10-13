package com.shuyun.sbd.utils.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Request;
import com.ning.http.client.Response;
import com.shuyun.sbd.utils.tools.Base64Operate;

/**
 * Component:
 * Description:
 * Date: 15/10/13
 *
 * @author yue.zhang
 */
public class Oauth2CodeClient {

    private static final String clientId = "api-developer";
    private static final String secret = "9c32a5a49645464fe05d7f3835cf51c4";

    private static final String username = "shuyunwyx";
    private static final String password = "shuyunwyx";

    private static final String oauthServerHost = "http://yunwan2.3322.org:57102";
//    private static final String oauthServerHost = "http://127.0.0.1:8083";
    private static final String resourceServerHost = "http://yunwan2.3322.org:57101";

    public AccessToken getAccessTokenByCode(String code,String redirectUri){

        AsyncHttpClient client = new AsyncHttpClient();
        try{
            String url = String.format(oauthServerHost + "/oauth/token?grant_type=authorization_code&code=%s&redirect_uri=%s&state=123",code,redirectUri);

            String basic = Base64Operate.getBASE64(clientId + ":" + secret);

            Request request = client.preparePost(url).setHeader("Authorization","Basic "+basic).build();

            Response response = client.executeRequest(request).get();

            AccessToken accessToken = null;
            if(response.getStatusCode()==200){
                String body = response.getResponseBody("utf-8");
                ObjectMapper mapper = new ObjectMapper();
                accessToken = mapper.readValue(body,AccessToken.class);
            }
            return accessToken;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            client.close();
        }
    }

    public String getTradeByTid(String tid,Long brandId,String accessToken){
        AsyncHttpClient client = new AsyncHttpClient();
        try{

            String url = String.format(resourceServerHost + "/rest?client_id=%s&client_secret=%s&api=shuyun.trade.get&tid=%s&brand_id=%s&fields=tid,status",clientId,secret,tid,brandId);

            Request request = client.preparePost(url).setHeader("Authorization","Bearer "+accessToken).build();

            Response response = client.executeRequest(request).get();

            String result = null;
            if(response.getStatusCode()==200){
                result = response.getResponseBody("utf-8");
            }
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            client.close();
        }
    }

    /**
     * 使用方法：
     * 1. http://yunwan2.3322.org:57102/oauth/authorize?response_type=code&client_id=api-developer&state=xyz%20&redirect_uri=http://www.baidu.com
     * 2. 复制code
     * @param args
     */
    public static void main(String [] args){

        String code = "B0FDDX";
        String redirectUri = "http://www.baidu.com";

        Oauth2CodeClient client = new Oauth2CodeClient();
        AccessToken accessToken = client.getAccessTokenByCode(code,redirectUri);
        System.out.println("===================access_token信息====================");
        System.out.println(accessToken.getAccessToken());

        String trade = client.getTradeByTid("83922345921037000",1L,accessToken.getAccessToken());
        System.out.println("===================订单信息====================");
        System.out.println(trade);
    }
}
