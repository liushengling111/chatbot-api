package com.lsl.chatbot.domain.zsxq.service;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import com.lsl.chatbot.domain.IZsxqApi;
import com.lsl.chatbot.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import com.lsl.chatbot.domain.zsxq.model.req.AnswerReq;
import com.lsl.chatbot.domain.zsxq.model.req.ReqData;
import com.lsl.chatbot.domain.zsxq.model.res.AnswerResp;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author liu'sheng'ling
 * @description
 * @create 2026/3/6 21:44
 */
@Slf4j
@Service
public class ZsxqApi implements IZsxqApi {

    @Override
    public UnAnsweredQuestionsAggregates queryUnAnsweredQuestionsTopicId(String groupId, String cookie) throws IOException {
        // 创建HttpClient对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建HttpGet对象，设置url访问地址
        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/" + groupId + "/topics?scope=unanswered_questions&count=20");
        // 设置请求头Cookie
        get.addHeader("cookie", cookie);
        // 设置请求头类型
        get.addHeader("Content-Type", "application/json;charset=utf8");
        CloseableHttpResponse response = httpClient.execute(get);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(response.getEntity());
            log.info("拉取提问数据。groupId：{} jsonStr：{}", groupId, jsonStr);
            return JSON.parseObject(jsonStr, UnAnsweredQuestionsAggregates.class);
        } else {
            throw new RuntimeException("queryUnAnsweredQuestionsTopicId Err Code is " + response.getStatusLine().getStatusCode());
        }
    }

    @Override
    public boolean answer(String groupId, String cookie, String topicId, String text, boolean silenced) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/"+topicId+"/answer");
        post.addHeader("cookie", cookie);
        post.addHeader("Content-Type","application/json;charset=utf8");
//        String json = "{\n" +
//                "    \"req_data\": {\n" +
//                "        \"text\": \"那就来吧！\\n\",\n" +
//                "        \"image_ids\": [],\n" +
//                "        \"mentioned_user_ids\": []\n" +
//                "    }\n" +
//                "}";
        AnswerReq answerReq = new AnswerReq(new ReqData(text, silenced));
        String paramJson = JSONObject.fromObject(answerReq).toString();
        StringEntity entity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(entity);
        CloseableHttpResponse response = httpClient.execute(post);
        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String jsonStr = EntityUtils.toString(response.getEntity());
            AnswerResp answerResp = JSON.parseObject(jsonStr, AnswerResp.class);
            return answerResp.isSucceeded();
        }else {
            throw new RuntimeException("answer Err Code is " + response.getStatusLine().getStatusCode());        }
    }

}