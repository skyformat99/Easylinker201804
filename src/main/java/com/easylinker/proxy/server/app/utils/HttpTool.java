package com.easylinker.proxy.server.app.utils;


import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;

@Component
public class HttpTool {
    private  MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private  OkHttpClient client = new OkHttpClient();

    /**
     * emq.api.host=http://localhost:8080/api/v2/
     * emq.api.user=admin
     * emq.api.password=public
     */

    @Value("${emq.api.user}")
    String username;
    @Value("${emq.api.password}")
    String password;

    /**
     * 发送http post请求
     *
     * @param url
     * @param data 提交的参数为key=value&key1=value1的形式
     * @return
     */
    public JSONObject postWithAuthorization(String url, JSONObject data) throws Exception {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), data.toJSONString());
        Request request = new Request.Builder()
                .addHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes()))
                .addHeader("Content-Length", "22")
                .addHeader("Content-Type", "application/json")
                .addHeader("Server", "MochiWeb/1.0 (Any of you quaids got a smint?)")
                .url(url)
                .post(body)
                .build();
        return JSONObject.parseObject(client.newCall(request).execute().body().string());

    }

}
