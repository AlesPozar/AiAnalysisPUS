package com.ales.aianalysis.api.dto;

import com.ales.aianalysis.entity.AnalysisSession;
import java.time.LocalDateTime;

public class AnalysisSessionResponse {
    public Long id;
    public Long analysisId;
    public String respondent;
    public LocalDateTime completedAt;

    public AnalysisSessionResponse(Long id, Long analysisId, String respondent, LocalDateTime completedAt) {
        this.id = id;
        this.analysisId = analysisId;
        this.respondent = respondent;
        this.completedAt = completedAt;
    }

    public static AnalysisSessionResponse fromEntity(AnalysisSession analysisSession) {
        return new AnalysisSessionResponse(
                analysisSession.getId(),
                analysisSession.getAnalysis().getId(),
                analysisSession.getRespondent(),
                analysisSession.getCompletedAt()
        );
    }

}
