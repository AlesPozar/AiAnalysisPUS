package com.ales.aianalysis.api.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.ales.aianalysis.entity.AnalysisSession;

public class FullAnalysisSessionResponse {
    public Long id;
    public Long analysisId;
    public String respondent;
    public LocalDateTime completedAt;
    public List<QuestionResponseFullResponse> responses;

    public FullAnalysisSessionResponse(Long id, Long analysisId, String respondent, LocalDateTime completedAt, List<QuestionResponseFullResponse> responses){
        this.id = id;
        this.analysisId = analysisId;
        this.respondent = respondent;
        this.completedAt = completedAt;
        this.responses = responses;
    }

    public static FullAnalysisSessionResponse fromEntity(AnalysisSession session, List<QuestionResponseFullResponse> responses){
        return new FullAnalysisSessionResponse(
                session.getId(),
                session.getAnalysis().getId(),
                session.getRespondent(),
                session.getCompletedAt(),
                responses
        );
    }
}
