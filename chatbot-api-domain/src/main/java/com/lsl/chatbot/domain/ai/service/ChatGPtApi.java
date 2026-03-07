package com.lsl.chatbot.domain.ai.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.lsl.chatbot.domain.ai.IChatGPtApi;
import com.lsl.chatbot.domain.ai.model.aggregates.AIAnswer;
import com.lsl.chatbot.domain.ai.model.vo.Choices;
import com.lsl.chatbot.domain.ai.model.vo.Message;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author liu'sheng'ling
 * @description ChatGPtApi
 * @create 2026/3/7 15:51
 */
@Service
public class ChatGPtApi implements IChatGPtApi {
    @Value("${chatbot-api.apiKey}")
    private String apiKey;
    @Override
    public String doOpenAi(String question) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://open.bigmodel.cn/api/paas/v4/chat/completions");
        post.addHeader("Authorization", "Bearer " + apiKey);
        post.addHeader("Content-Type","application/json;charset=utf8");
        String paramJson = "{\n" +
                "    \"model\": \"glm-5\",\n" +
                "    \"messages\": [\n" +
                "        {\n" +
                "            \"role\": \"system\",\n" +
                "            \"content\": \"你是一个有用的AI助手。\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"role\": \"user\",\n" +
                "            \"content\": \"" + question + "\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"temperature\": 1.0,\n" +
                "    \"stream\": false\n" +
                "}";
        StringEntity entity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(entity);
        CloseableHttpResponse response = httpClient.execute(post);
        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String jsonStr = EntityUtils.toString(response.getEntity());
            AIAnswer answer = JSON.parseObject(jsonStr, AIAnswer.class);
            StringBuilder sb = new StringBuilder();
            List<Choices> choices = answer.getChoices();
            if (choices != null && !choices.isEmpty()) {
                Message message = choices.get(0).getMessage();
                if (message != null) {
                    sb.append(message.getContent());
                }
            }
            return sb.toString();
        }else {
            throw new RuntimeException("请求失败，错误码是" + response.getStatusLine().getStatusCode());
        }
    }
}