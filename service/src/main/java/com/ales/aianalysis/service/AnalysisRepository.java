package com.ales.aianalysis.service;

import java.util.List;
import java.util.Optional;

import com.ales.aianalysis.entity.Analysis;

import jakarta.persistence.EntityManager;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

// ponovi se enkrat, request scope je za web request, session scope je za user session, application scope je za celotno aplikacijo
@ApplicationScoped
public class AnalysisRepository {

    @Inject
    EntityManager entityManager;
    public void persist(Analysis analysis) {
        entityManager.persist(analysis);
    }


    // JPQL ni avg SQL in rab/iscemo po entitijih, zato a kot objekt in ne *
    public List<Analysis> findAll() {
        return entityManager.createQuery(
            "SELECT a FROM Analysis a", Analysis.class
        ).getResultList();
    }

    public Optional<Analysis> findById(Long id) {
        Analysis analysis = entityManager.find(Analysis.class, id);
        return Optional.ofNullable(analysis);
    }

    public Optional<Analysis> findByPublicCode(String publicCode) {
        return entityManager
                .createQuery("SELECT a FROM Analysis a WHERE a.publicCode = :publicCode", Analysis.class)
                .setParameter("publicCode", publicCode)
                .getResultStream()
                .findFirst();
    }

    public void delete(Analysis analysis) {
        entityManager.remove(analysis);
    }
    
}
