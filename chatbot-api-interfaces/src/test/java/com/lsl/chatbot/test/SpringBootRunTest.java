package com.lsl.chatbot.test;

import com.alibaba.fastjson.JSON;
import com.lsl.chatbot.domain.IZsxqApi;
import com.lsl.chatbot.domain.ai.IChatGPtApi;
import com.lsl.chatbot.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import com.lsl.chatbot.domain.zsxq.model.vo.Topics;
import com.lsl.chatbot.domain.zsxq.service.ZsxqApi;
import com.lsl.chatbot.interfaces.ApiApplication;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

/**
 * @author liu'sheng'ling
 * @description
 * @create 2026/3/6 22:23
 */
@Slf4j
@SpringBootTest(classes = ApiApplication.class)
@RunWith(SpringRunner.class)
public class SpringBootRunTest {
    @Value("${chatbot-api.groupId}")
    private String groupId;
    @Value("${chatbot-api.cookie}")
    private String cookie;
    @Resource
    private IZsxqApi zsxqApi;
    @Resource
    private IChatGPtApi chatGPtApi;
    @Test
    public void test_zsxqApi() throws IOException {
        UnAnsweredQuestionsAggregates unAnsweredQuestionsAggregates = zsxqApi.queryUnAnsweredQuestionsTopicId(groupId, cookie);
        log.info("测试结果：{}", JSON.toJSONString(unAnsweredQuestionsAggregates));

        List<Topics> topics = unAnsweredQuestionsAggregates.getResp_data().getTopics();
        for (Topics topic : topics) {
            String topicId = topic.getTopic_id();
            String text = topic.getQuestion().getText();
            log.info("topicId：{} text：{}", topicId, text);
          // 回答问题
            zsxqApi.answer(groupId, cookie, topicId, text, false);
        }
    }
    @Test
    public void test_OpenAi() throws IOException {
        String answer = chatGPtApi.doOpenAi("用java帮我写一个冒泡排序");
        log.info("测试结果：{}", answer);
    }

}