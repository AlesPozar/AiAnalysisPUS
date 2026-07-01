package com.ales.aianalysis.api.dto;

import java.util.List;

import com.ales.aianalysis.entity.QuestionResponse;

public class QuestionResponseFullResponse {
    public Long id;
    public Long questionId;
    public String questionText;
    public List<ResponseMessageResponse> messages;

    public QuestionResponseFullResponse(Long id, Long questionId, String questionText, List<ResponseMessageResponse> messages){
        this.id = id;
        this.questionId = questionId;
        this.questionText = questionText;
        this.messages = messages;
    }

    public static QuestionResponseFullResponse fromEntity(QuestionResponse questionResponse, List<ResponseMessageResponse> messages) {
        return new QuestionResponseFullResponse(
                questionResponse.getId(),
                questionResponse.getQuestion().getId(),
                questionResponse.getQuestion().getQuestionText(),
                messages
        );
    }
}