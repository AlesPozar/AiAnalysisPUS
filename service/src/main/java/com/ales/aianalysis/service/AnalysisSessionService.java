package com.ales.aianalysis.service;

import com.ales.aianalysis.entity.Analysis;
import com.ales.aianalysis.entity.AnalysisSession;
import com.ales.aianalysis.entity.MessageSender;
import com.ales.aianalysis.entity.QuestionResponse;
import com.ales.aianalysis.entity.ResponseMessage;
import com.ales.aianalysis.entity.Question;
import com.ales.aianalysis.service.dtoCopy.CreateFullAnalysisSessionCopy;
import com.ales.aianalysis.service.dtoCopy.CreateQuestionResponseCopy;
import com.ales.aianalysis.service.dtoCopy.CreateResponseMessageCopy;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class AnalysisSessionService {
    
    @Inject
    AnalysisSessionRepository analysisSessionRepository;

    @Inject
    AnalysisRepository analysisRepository;

    @Transactional
    public AnalysisSession createAnalysisSession(Long analysisId, String respondent) {
        Analysis analysis = analysisRepository.findById(analysisId)
                .orElseThrow(() -> new IllegalArgumentException("Analysis not found"));

        AnalysisSession analysisSession = new AnalysisSession(
                analysis,
                respondent
        );

        analysisSessionRepository.persist(analysisSession);

        return analysisSession;
    }

    public List<AnalysisSession> getSessionsByAnalysisId(Long analysisId) {
        return analysisSessionRepository.findByAnalysisId(analysisId);
    }

    public AnalysisSession getSessionById(Long sessionId) {
        return analysisSessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Analysis session not found"));
    }

    public AnalysisSession getSessionByAnalysisIdAndSessionId(Long analysisId, Long sessionId) {
        AnalysisSession session = getSessionById(sessionId);
        if (!session.getAnalysis().getId().equals(analysisId)) {
            throw new IllegalArgumentException("Analysis session does not belong to the specified analysis");
        }
        return session;
    }

    //se dodatno za implementiranje post vseh vprasanj na koncu
    @Inject
    QuestionRepository questionRepository;

    @Inject
    QuestionResponseRepository questionResponseRepository;

    @Inject
    ResponseMessageRepository responseMessageRepository;

    @Transactional
    public AnalysisSession createFullAnalysisSession(Long analysisId, CreateFullAnalysisSessionCopy request){
        validateFullAnalysisSessionRequest(request);

        Analysis analysis = analysisRepository.findById(analysisId).orElseThrow(()->new IllegalArgumentException("Analysis not found"));

        AnalysisSession session = new AnalysisSession(analysis, request.respondent);

        analysisSessionRepository.persist(session);

        for(CreateQuestionResponseCopy responseRequest : request.responses){
            Question question = questionRepository.findById(responseRequest.questionId).orElseThrow(()->new IllegalArgumentException("Question not found"));

            if(!question.getAnalysis().getId().equals(analysisId)){
                throw new IllegalArgumentException("Question does not belong to this analysis");
            }

            QuestionResponse questionResponse = new QuestionResponse(
                    session,
                    question
            );

            questionResponseRepository.persist(questionResponse);

            for(CreateResponseMessageCopy messageRequest : responseRequest.messages){
                ResponseMessage responseMessage = new ResponseMessage(questionResponse, MessageSender.valueOf(messageRequest.sender), messageRequest.messageText, messageRequest.position);
                responseMessageRepository.persist(responseMessage);
            }
        }
        return session;
    }


    // primer metode za validacijo
    private void validateFullAnalysisSessionRequest(CreateFullAnalysisSessionCopy request) {
        // logika like
        if(request == null){
            throw new IllegalArgumentException("Request cannot be null");
        }
        if(request.respondent == null || request.respondent.isEmpty()){
            throw new IllegalArgumentException("Respondent cannot be null or empty");
        }
        // itd
    }
}
