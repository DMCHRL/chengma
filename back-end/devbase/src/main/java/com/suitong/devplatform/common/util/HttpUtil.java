package com.suitong.devplatform.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.suitong.devplatform.config.Constants;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;

public class HttpUtil {

    public static String httpPost(String url,JSONObject jsonParam, boolean noNeedResponse) throws Exception {
        //post请求返回结果
    	CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost method = new HttpPost(url);
        String str = "";
        try {
            if (null != jsonParam) {
               //解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(), Constants.CHAR_SET);
                entity.setContentEncoding(Constants.CHAR_SET);
                entity.setContentType("application/json");
                method.setEntity(entity);
            }
            HttpResponse result = httpClient.execute(method);
            url = URLDecoder.decode(url, Constants.CHAR_SET);
            /**请求发送成功，并得到响应**/
            if (result.getStatusLine().getStatusCode() == 200) {
               try {
                    /**读取服务器返回过来的json字符串数据**/
                    str = EntityUtils.toString(result.getEntity());
                    if (noNeedResponse) {
                        return null;
                    }
                } catch (Exception e) {
                   e.printStackTrace();
                }
            } else {
            	throw new Exception();
            }
        } catch (IOException e) {
        	throw new Exception();
           // e.printStackTrace();
        }
        return str;
    }

    //获取请求的参数
    public static String post(HttpServletRequest request) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), Constants.CHAR_SET));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while((line = br.readLine())!=null){
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
