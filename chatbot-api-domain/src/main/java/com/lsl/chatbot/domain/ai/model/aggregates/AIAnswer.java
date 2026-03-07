package com.lsl.chatbot.domain.ai.model.aggregates;

import com.lsl.chatbot.domain.ai.model.vo.Choices;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author liu'sheng'ling
 * @description AI回答
 * @create 2026/3/7 16:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AIAnswer {
    private String id;
    private String object;
    private int created;
    private String model;
    private List<Choices> choices;
}