package com.lsl.chatbot.domain.ai;

import java.io.IOException;

/**
 * @author liu'sheng'ling
 * @description
 * @create 2026/3/7 12:07
 */
public interface IChatGPtApi {

    String doOpenAi(String question) throws IOException;

}