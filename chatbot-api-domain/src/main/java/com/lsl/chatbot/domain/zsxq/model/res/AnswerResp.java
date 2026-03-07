package com.lsl.chatbot.domain.zsxq.model.res;

import com.lsl.chatbot.domain.zsxq.model.req.ReqData;

/**
 * @author liu'sheng'ling
 * @description 回答响应结果
 * @create 2026/3/6 22:00
 */
public class AnswerResp {

   private boolean succeeded;

    public boolean isSucceeded() {
        return succeeded;
    }

    public void setSucceeded(boolean succeeded) {
        this.succeeded = succeeded;
    }
}