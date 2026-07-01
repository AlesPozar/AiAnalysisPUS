package com.ales.aianalysis.api;

import com.ales.aianalysis.api.dto.AnalysisSessionResponse;
import com.ales.aianalysis.api.dto.CreateAnalysisSessionRequest;
import com.ales.aianalysis.api.dto.CreateFullAnalysisSessionRequest;
import com.ales.aianalysis.api.dto.CreateQuestionResponseRequest;
import com.ales.aianalysis.api.dto.CreateResponseMessageRequest;
import com.ales.aianalysis.api.dto.FullAnalysisSessionResponse;
import com.ales.aianalysis.api.dto.QuestionResponseFullResponse;
import com.ales.aianalysis.api.dto.ResponseMessageResponse;
import com.ales.aianalysis.entity.AnalysisSession;
import com.ales.aianalysis.service.AnalysisSessionService;
import com.ales.aianalysis.service.QuestionResponseRepository;
import com.ales.aianalysis.service.ResponseMessageRepository;
import com.ales.aianalysis.service.dtoCopy.CreateFullAnalysisSessionCopy;
import com.ales.aianalysis.service.dtoCopy.CreateQuestionResponseCopy;
import com.ales.aianalysis.service.dtoCopy.CreateResponseMessageCopy;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;



@Path("/analyses/{analysisId}/sessions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AnalysisSessionResource {
    
    @Inject
    AnalysisSessionService analysisSessionService;

    @Inject
    QuestionResponseRepository questionResponseRepository;

    @Inject
    ResponseMessageRepository responseMessageRepository;

    @POST
    public AnalysisSessionResponse createAnalysisSession(@PathParam("analysisId") Long analysisId, CreateAnalysisSessionRequest request) {
        AnalysisSession analysisSession = analysisSessionService.createAnalysisSession(analysisId, request.respondent);
        return AnalysisSessionResponse.fromEntity(analysisSession);
    }

    @GET
    public List<AnalysisSessionResponse> getSessionsByAnalysis(@PathParam("analysisId") Long analysisId) {
        return analysisSessionService.getSessionsByAnalysisId(analysisId)
            .stream()
            .map(AnalysisSessionResponse::fromEntity)
            .toList();
    }

    @GET
    @Path("/{sessionId}")
    public AnalysisSessionResponse getSessionByAnalysisIdAndSessionId(@PathParam("analysisId") Long analysisId, @PathParam("sessionId") Long sessionId) {
        return AnalysisSessionResponse.fromEntity(analysisSessionService.getSessionByAnalysisIdAndSessionId(analysisId, sessionId));
    }


    // ponovno si poglej ti dve funkciji
    @GET
    @Path("/{sessionId}/full")
    public FullAnalysisSessionResponse getFullAnalysisSession(
            @PathParam("analysisId") Long analysisId,
            @PathParam("sessionId") Long sessionId
    ) {
        AnalysisSession session = analysisSessionService.getSessionByAnalysisIdAndSessionId(
                analysisId,
                sessionId
        );

        List<QuestionResponseFullResponse> responses = questionResponseRepository
                .findBySessionId(sessionId)
                .stream()
                .map(questionResponse -> {
                    List<ResponseMessageResponse> messages = responseMessageRepository
                            .findByQuestionResponseId(questionResponse.getId())
                            .stream()
                            .map(ResponseMessageResponse::fromEntity)
                            .toList();

                    return QuestionResponseFullResponse.fromEntity(
                            questionResponse,
                            messages
                    );
                })
                .toList();

        return FullAnalysisSessionResponse.fromEntity(
                session,
                responses
        );
    }

    @POST
    @Path("/full")
    public AnalysisSessionResponse createFullAnalysisSession(
            @PathParam("analysisId") Long analysisId,
            CreateFullAnalysisSessionRequest request
    ) {
        CreateFullAnalysisSessionCopy command = new CreateFullAnalysisSessionCopy();
        command.respondent = request.respondent;
        command.responses = new java.util.ArrayList<>();

        for(CreateQuestionResponseRequest responseRequest : request.responses){
            CreateQuestionResponseCopy responseCopy = new CreateQuestionResponseCopy();
            responseCopy.questionId = responseRequest.questionId;
            responseCopy.messages = new java.util.ArrayList<>();

            for(CreateResponseMessageRequest messageRequest : responseRequest.messages){
                CreateResponseMessageCopy messageCopy = new CreateResponseMessageCopy();
                messageCopy.sender = messageRequest.sender;
                messageCopy.messageText = messageRequest.messageText;
                messageCopy.position = messageRequest.position;

                responseCopy.messages.add(messageCopy);
            }

            command.responses.add(responseCopy);
        }

        AnalysisSession session = analysisSessionService.createFullAnalysisSession(
                analysisId,
                command
        );

        return AnalysisSessionResponse.fromEntity(session);
    }
}
