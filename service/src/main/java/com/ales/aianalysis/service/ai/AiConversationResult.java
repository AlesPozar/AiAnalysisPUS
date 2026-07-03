package com.ales.aianalysis.service.ai;

public class AiConversationResult {
    public AiConversationState state;
    public String messageText;

    public AiConversationResult(AiConversationState state, String messageText) {
        this.state = state;
        this.messageText = messageText;
    }
}