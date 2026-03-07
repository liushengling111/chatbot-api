package com.lsl.chatbot.interfaces;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author liu'sheng'ling
 * @description
 * @create 2026/3/6 12:44
 */
@SpringBootApplication(scanBasePackages = {"com.lsl.chatbot.interfaces", "com.lsl.chatbot.domain"})
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

}