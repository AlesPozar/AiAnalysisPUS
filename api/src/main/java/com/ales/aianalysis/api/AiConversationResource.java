package com.ales.aianalysis.api;

import java.util.ArrayList;

import com.ales.aianalysis.api.dto.ai.AiConversationMessageRequest;
import com.ales.aianalysis.api.dto.ai.AiConversationRequest;
import com.ales.aianalysis.api.dto.ai.AiConversationResponse;
import com.ales.aianalysis.api.dto.ai.AiPreviousResponseRequest;
import com.ales.aianalysis.service.AiConversationService;
import com.ales.aianalysis.service.ai.AiConversationCommand;
import com.ales.aianalysis.service.ai.AiConversationMessageInput;
import com.ales.aianalysis.service.ai.AiConversationResult;
import com.ales.aianalysis.service.ai.AiPreviousResponseInput;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/analyses/{analysisId}/questions/{questionId}/ai/conversation")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AiConversationResource {
    
    @Inject
    AiConversationService aiConversationService;

    @POST
    public AiConversationResponse continueConversation(@PathParam("analysisId") Long analysisId, @PathParam("questionId") Long questionId, AiConversationRequest request){
        // request pretvorimo v command, ker service nima dostopa do dto-jev od /api-ja
        AiConversationCommand command = new AiConversationCommand();
        command.previousResponses = new ArrayList<>();

        // za previous responses torej vprasanja se sumary and shii prepise notr
        if(request.previousResponses != null){
            for(AiPreviousResponseRequest previousResponseRequest : request.previousResponses){
                AiPreviousResponseInput previousResponseInput = new AiPreviousResponseInput();
                previousResponseInput.questionId = previousResponseRequest.questionId;
                previousResponseInput.summary = previousResponseRequest.summary;
                command.previousResponses.add(previousResponseInput);
            }
        }

        // za messages torej trenutno vprasanje
        command.messages = new ArrayList<>();

        if(request.messages != null){
            for(AiConversationMessageRequest messageRequest : request.messages){
                AiConversationMessageInput messageInput = new AiConversationMessageInput();
                messageInput.sender = messageRequest.sender;
                messageInput.messageText = messageRequest.messageText;
                command.messages.add(messageInput);
            }
        }
        
        // service layer call
        AiConversationResult result = aiConversationService.continueConversation(analysisId,questionId,command);
        // result pretvorimo v response
        return new AiConversationResponse(result.state.name(), result.messageText);
    }
}
