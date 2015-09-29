package com.shuyun.sbd.utils.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ning.http.client.Request;
import com.ning.http.client.Response;
import com.shuyun.sbd.utils.tools.Base64Operate;
import com.ning.http.client.AsyncHttpClient;

/**
 * Component: 采用密码模式进行授权
 * Description:
 * Date: 15/8/7
 *
 * @author yue.zhang
 */
public class Oauth2Client {

    private static final String clientId = "api-developer";
    private static final String secret = "9c32a5a49645464fe05d7f3835cf51c4";

    private static final String username = "shuyunwyx";
    private static final String password = "shuyunwyx";

    private static final String oauthServerHost = "http://yunwan2.3322.org:57102";
    private static final String resourceServerHost = "http://yunwan2.3322.org:57101";

    /**
     * 获取accesstoken
     *
     * Authorization: Basic czZCaGRSa3F0MzpnWDFmQmF0M2JW
     */
    public AccessToken getAccessToken(){
        AsyncHttpClient client = new AsyncHttpClient();
        try{
            String url = String.format(oauthServerHost + "/oauth/token?grant_type=password&username=%s&password=%s",username,password);

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

    public AccessToken refreshToken(String refreshToken){
        AsyncHttpClient client = new AsyncHttpClient();
        try{
            String url = String.format(oauthServerHost + "/oauth/token?grant_type=refresh_token&refresh_token=%s",refreshToken);

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


    public static void main(String [] args){

        Oauth2Client client = new Oauth2Client();

        AccessToken accessToken = client.getAccessToken();
        System.out.println("===================access_token信息====================");
        System.out.println(accessToken.getAccessToken());

//        String trade = client.getTradeByTid("37317684094965000",1L,accessToken.getAccessToken());
//        System.out.println("===================订单信息====================");
//        System.out.println(trade);
//
//        // 使用refreshToken再获取一次accessToken
//        accessToken = client.refreshToken(accessToken.getRefreshToken());
//        trade = client.getTradeByTid("37317684094965000",1L,accessToken.getAccessToken());
//        System.out.println("===================订单信息====================");
//        System.out.println(trade);

    }

}
