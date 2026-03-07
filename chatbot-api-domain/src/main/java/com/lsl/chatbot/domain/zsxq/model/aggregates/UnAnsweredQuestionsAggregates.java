package com.lsl.chatbot.domain.zsxq.model.aggregates;

import com.lsl.chatbot.domain.zsxq.model.res.RespData;

/**
 * @author liu'sheng'ling
 * @description 未回答问题的聚合信息
 * @create 2026/3/6 21:16
 */
public class UnAnsweredQuestionsAggregates {

    private boolean succeeded;
    private RespData resp_data;

    public RespData getResp_data() {
        return resp_data;
    }

    public void setResp_data(RespData resp_data) {
        this.resp_data = resp_data;
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    public void setSucceeded(boolean succeeded) {
        this.succeeded = succeeded;
    }
}