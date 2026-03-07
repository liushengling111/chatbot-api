package com.lsl.chatbot.test;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;


/**
 * @author liu'sheng'ling
 * @description 单元测试
 * @create 2026/3/6 12:47
 */
public class ApiTest {

        @Test
        public void query_unanswered_questions() throws IOException {
                // 创建HttpClient对象
                CloseableHttpClient httpClient = HttpClientBuilder.create().build();
                // 创建HttpGet对象，设置url访问地址
                HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/15554854241242/topics?scope=all&count=1");
                // 设置请求头Cookie
                get.addHeader("cookie","zsxq_access_token=139A3FBF-4AB6-4915-83D7-FDE19ADF1A47_34537A91A05D0C25; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22584111188512414%22%2C%22first_id%22%3A%2219bc0cb0fee870-00ed8756e0e2fe6-26061a51-1327104-19bc0cb0fef1d05%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTliYzBjYjBmZWU4NzAtMDBlZDg3NTZlMGUyZmU2LTI2MDYxYTUxLTEzMjcxMDQtMTliYzBjYjBmZWYxZDA1IiwiJGlkZW50aXR5X2xvZ2luX2lkIjoiNTg0MTExMTg4NTEyNDE0In0%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22584111188512414%22%7D%2C%22%24device_id%22%3A%2219bc0cb0fee870-00ed8756e0e2fe6-26061a51-1327104-19bc0cb0fef1d05%22%7D; abtest_env=product\n");
                // 设置请求头类型
                get.addHeader("Content-Type","application/json;charset=utf8");
                CloseableHttpResponse response = httpClient.execute(get);
                if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                        String res = EntityUtils.toString(response.getEntity());
                        System.out.println(res);
                }else{
                        System.out.println(response.getStatusLine().getStatusCode());
                }
        }
        @Test
        public void reply_question() throws IOException {
                CloseableHttpClient httpClient = HttpClientBuilder.create().build();
                HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/45811242555554188/answer");
                post.addHeader("cookie", "zsxq_access_token=139A3FBF-4AB6-4915-83D7-FDE19ADF1A47_34537A91A05D0C25; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22584111188512414%22%2C%22first_id%22%3A%2219bc0cb0fee870-00ed8756e0e2fe6-26061a51-1327104-19bc0cb0fef1d05%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTliYzBjYjBmZWU4NzAtMDBlZDg3NTZlMGUyZmU2LTI2MDYxYTUxLTEzMjcxMDQtMTliYzBjYjBmZWYxZDA1IiwiJGlkZW50aXR5X2xvZ2luX2lkIjoiNTg0MTExMTg4NTEyNDE0In0%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22584111188512414%22%7D%2C%22%24device_id%22%3A%2219bc0cb0fee870-00ed8756e0e2fe6-26061a51-1327104-19bc0cb0fef1d05%22%7D; abtest_env=product\n");
                post.addHeader("Content-Type","application/json;charset=utf8");
                String json = "{\n" +
                        "    \"req_data\": {\n" +
                        "        \"text\": \"那就来吧！\\n\",\n" +
                        "        \"image_ids\": [],\n" +
                        "        \"mentioned_user_ids\": []\n" +
                        "    }\n" +
                        "}";
                StringEntity entity = new StringEntity(json, ContentType.create("text/json", "UTF-8"));
                post.setEntity(entity);
                CloseableHttpResponse response = httpClient.execute(post);
                if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                        String res = EntityUtils.toString(response.getEntity());
                        System.out.println(res);
                }else {
                        System.out.println(response.getStatusLine().getStatusCode());
                }
        }
        @Test
        public void test_OpenAi() throws IOException {
                CloseableHttpClient httpClient = HttpClientBuilder.create().build();
                HttpPost post = new HttpPost("https://open.bigmodel.cn/api/paas/v4/chat/completions");
                post.addHeader("Authorization", "Bearer af389e3bb12946a58b08ab0f9bca63d9.qOTRPTEMP1wD35E8");
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
                        "            \"content\": \"你好，帮我写一个冒泡排序。\"\n" +
                        "        }\n" +
                        "    ],\n" +
                        "    \"temperature\": 1.0,\n" +
                        "    \"stream\": false\n" +
                        "}";
                StringEntity entity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
                post.setEntity(entity);
                CloseableHttpResponse response = httpClient.execute(post);
                if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                        String res = EntityUtils.toString(response.getEntity());
                        System.out.println("完整响应：" + res);
                }else {
                        System.out.println("错误码：" + response.getStatusLine().getStatusCode());
                }
        }
}