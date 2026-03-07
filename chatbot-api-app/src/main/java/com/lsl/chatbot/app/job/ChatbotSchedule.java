package com.lsl.chatbot.app.job;

import com.lsl.chatbot.domain.IZsxqApi;
import com.lsl.chatbot.domain.ai.IChatGPtApi;
import com.lsl.chatbot.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import com.lsl.chatbot.domain.zsxq.model.vo.Topics;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;

/**
 * @author liu'sheng'ling
 * @description 收集zsxq问题，ai定时回答
 * @create 2026/3/7 18:09
 */
@Slf4j
@Configuration
@EnableScheduling
public class ChatbotSchedule {
    @Value("${chatbot-api.groupId}")
    private String groupId;
    @Value("${chatbot-api.cookie}")
    private String cookie;
    @Resource
    private IChatGPtApi chatGPt;
    @Resource
    private IZsxqApi zsxqApi;
    @Scheduled(cron = "0/30 * * * * ?")
    public void run() {
        try {
            if (new Random().nextBoolean()) {
                log.info("随机打烊中...");
                return;
            }
            LocalTime now = LocalTime.now();
            int hour = now.getHour();
            if(hour >= 22 || hour < 6){
                log.info("当前时间：{} 不在回答时间段内", now);
                return;
            }
            // 1. 收集问题
            UnAnsweredQuestionsAggregates unAnsweredQuestionsAggregates = zsxqApi.queryUnAnsweredQuestionsTopicId(groupId, cookie);
            List<Topics> topics = unAnsweredQuestionsAggregates.getResp_data().getTopics();
            if (null == topics || topics.isEmpty()) {
                log.info("本次检索未查询到待会答问题");
                return;
            }
            Topics topic = topics.get(0);
            String topicId = topic.getTopic_id();
            String text = topic.getQuestion().getText();
            log.info("topicId：{} text：{}", topicId, text);
            // 2. AI回答
            String response = chatGPt.doOpenAi(text);
            // 3. 回复问题
            boolean status = zsxqApi.answer(groupId, cookie, topicId, response, false);
            log.info("编号：{} 问题：{} 回答：{} 状态：{}", topicId, text, response, status);
        } catch ( Exception e ){
            e.printStackTrace();
        }
    }
}