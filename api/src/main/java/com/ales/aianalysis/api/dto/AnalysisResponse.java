package com.ales.aianalysis.api.dto;

import java.time.LocalDateTime;

import com.ales.aianalysis.entity.Analysis;

public class AnalysisResponse {
    public Long id;
    public String title;
    public String description;
    public String publicCode;
    public LocalDateTime createdAt;

    public AnalysisResponse(Long id, String title, String description, String publicCode, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.publicCode = publicCode;
        this.createdAt = createdAt;
    }

    public static AnalysisResponse fromEntity(Analysis analysis) {
        return new AnalysisResponse(
                analysis.getId(),
                analysis.getTitle(),
                analysis.getDescription(),
                analysis.getPublicCode(),
                analysis.getCreatedAt()
        );
    }
}
