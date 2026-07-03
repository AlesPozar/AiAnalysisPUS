package com.ales.aianalysis.api.dto.ai;

import java.util.List;

public class AiConversationRequest {
    public List<AiPreviousResponseRequest> previousResponses;
    public List<AiConversationMessageRequest> messages;
}