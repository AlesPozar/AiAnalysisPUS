package com.ales.aianalysis.service;

import com.ales.aianalysis.entity.Analysis;
import com.ales.aianalysis.entity.AnalysisSession;

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
}
