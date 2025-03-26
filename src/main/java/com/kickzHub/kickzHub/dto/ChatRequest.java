package com.kickzHub.kickzHub.dto;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class ChatRequest {
    private String model;
    private List<Message> messages;
    private double temperature;
    private int max_tokens;

    public ChatRequest(String model, String prompt) {
        this.model = model;
        this.messages = new ArrayList<>();
        this.messages.add(new Message("system", "You are a helpful book recommendation assistant. You have access to our book database and can provide detailed information about books and make recommendations based on user preferences."));
        this.messages.add(new Message("user", prompt));
        this.temperature = 0.7;
        this.max_tokens = 1000;
    }
} 