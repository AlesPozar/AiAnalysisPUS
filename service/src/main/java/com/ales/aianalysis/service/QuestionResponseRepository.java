package com.ales.aianalysis.service;

import com.ales.aianalysis.entity.QuestionResponse;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class QuestionResponseRepository {
    
    @Inject
    EntityManager entityManager;

    public void persist(QuestionResponse questionResponse) {
        entityManager.persist(questionResponse);
    }

    public Optional<QuestionResponse> findById(Long id) {
        return Optional.ofNullable(entityManager.find(QuestionResponse.class, id));
    }

    public List<QuestionResponse> findBySessionId(Long sessionId) {
        return entityManager
                .createQuery(
                        "SELECT qr FROM QuestionResponse qr WHERE qr.session.id = :sessionId",
                        QuestionResponse.class
                )
                .setParameter("sessionId", sessionId)
                .getResultList();
    }

    public Optional<QuestionResponse> findBySessionIdAndQuestionId(Long sessionId, Long questionId) {
        return entityManager
                .createQuery(
                        "SELECT qr FROM QuestionResponse qr WHERE qr.session.id = :sessionId AND qr.question.id = :questionId",
                        QuestionResponse.class
                )
                .setParameter("sessionId", sessionId)
                .setParameter("questionId", questionId)
                .getResultStream()
                .findFirst();
    }
}
