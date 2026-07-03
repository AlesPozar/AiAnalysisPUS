package com.ales.aianalysis.api.dto.ai;

public class AiConversationResponse {
    public String state;
    public String messageText;

    public AiConversationResponse(String state, String messageText) {
        this.state = state;
        this.messageText = messageText;
    }
}