package com.lsl.chatbot.domain.ai.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liu'sheng'ling
 * @description 选择
 * @create 2026/3/7 15:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Choices {
    // 内容
    private Message message;
    // 索引
    private int index;
    // 结束
    private String finish_reason;
}