package com.lsl.chatbot.domain.zsxq.model.req;

/**
 * @author liu'sheng'ling
 * @description 回答请求参数
 * @create 2026/3/6 21:55
 */
public class AnswerReq {

    private ReqData req_data;

    public AnswerReq(ReqData req_data) {
        this.req_data = req_data;
    }
    public void setReq_data(ReqData req_data) {
        this.req_data = req_data;
    }
    public ReqData getReq_data() {
        return req_data;
    }
}