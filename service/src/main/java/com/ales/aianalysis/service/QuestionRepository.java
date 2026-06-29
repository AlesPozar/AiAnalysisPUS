package com.ales.aianalysis.service;

import java.util.Optional;

import com.ales.aianalysis.entity.Question;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

@ApplicationScoped
public class QuestionRepository {
    @Inject
    EntityManager entityManager;

    // kreiranje
    public void persist(Question question) {
        entityManager.persist(question);
    }

    // dobimo po id
    public Optional<Question> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Question.class, id));
    }

    // dobimo po id analize
    public List<Question> findByAnalysisId(Long analysisId) {
        return entityManager
            .createQuery(
                    "SELECT q FROM Question q WHERE q.analysis.id = :analysisId ORDER BY q.position ASC",
                    Question.class
            )
            .setParameter("analysisId", analysisId)
            .getResultList();
    }

    // dobimo po id analize in poziciji
    public Optional<Question> findByAnalysisIdAndPosition(Long analysisId, Integer position){
        return entityManager
            .createQuery(
                "SELECT q FROM Question q WHERE q.analysis.id = :analysisId AND q.position = :position",
                Question.class
            )
            .setParameter("analysisId", analysisId)
            .setParameter("position", position)
            .getResultStream().findFirst();
    }
}
