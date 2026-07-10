package com.ales.aianalysis.service;

import java.util.Optional;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;

import com.ales.aianalysis.entity.AnalysisSession;

@ApplicationScoped
public class AnalysisSessionRepository {
    @Inject
    private EntityManager entityManager;

    public void persist(AnalysisSession analysisSession) {
        entityManager.persist(analysisSession);
    }

    public Optional<AnalysisSession> findById(Long id) {
        return Optional.ofNullable(entityManager.find(AnalysisSession.class, id));
    }

    public List<AnalysisSession> findByAnalysisId(Long analysisId) {
        return entityManager.createQuery("SELECT s FROM AnalysisSession s WHERE s.analysis.id = :analysisId ORDER BY s.completedAt DESC", AnalysisSession.class)
                .setParameter("analysisId", analysisId)
                .getResultList();
    }

    public void delete(AnalysisSession analysisSession) {
        entityManager.remove(analysisSession);
    }
}
