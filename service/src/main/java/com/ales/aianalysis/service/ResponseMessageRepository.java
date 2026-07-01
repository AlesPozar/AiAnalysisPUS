package com.ales.aianalysis.service;

import com.ales.aianalysis.entity.ResponseMessage;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;



@ApplicationScoped
public class ResponseMessageRepository {
    
    @Inject
    EntityManager entityManager;

    public void persist(ResponseMessage responseMessage) {
        entityManager.persist(responseMessage);
    }

    public Optional<ResponseMessage> findById(Long id) {
        return Optional.ofNullable(entityManager.find(ResponseMessage.class, id));
    }

    public List<ResponseMessage> findByQuestionResponseId(Long questionResponseId) {
        return entityManager
                .createQuery("SELECT rm FROM ResponseMessage rm WHERE rm.questionResponse.id = :questionResponseId ORDER BY rm.position ASC", ResponseMessage.class)
                .setParameter("questionResponseId", questionResponseId)
                .getResultList();
    }
}
