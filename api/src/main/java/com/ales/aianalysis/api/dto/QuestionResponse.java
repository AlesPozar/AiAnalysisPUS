package com.ales.aianalysis.api.dto;

import com.ales.aianalysis.entity.Question;

import java.time.LocalDateTime;


public class QuestionResponse {
    public Long id;
    public Long analysisId;
    public String questionText;
    public Integer position;
    public LocalDateTime createdAt;

    // navadna
    public QuestionResponse(Long id, Long analysisId, String questionText, Integer position, LocalDateTime createdAt) {
        this.id = id;
        this.analysisId = analysisId;
        this.questionText = questionText;
        this.position = position;
        this.createdAt = createdAt;
    }

    // preko classa intitete
    public static QuestionResponse fromEntity(Question question) {
        return new QuestionResponse(
                question.getId(),
                question.getAnalysis().getId(),
                question.getQuestionText(),
                question.getPosition(),
                question.getCreatedAt()
        );
    }
    
}
