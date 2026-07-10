package com.ales.aianalysis.service;

import java.util.UUID;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import com.ales.aianalysis.entity.Analysis;

@ApplicationScoped
public class AnalysisService {

    @Inject
    AnalysisRepository analysisRepository;

    @Transactional
    public Analysis createAnalysis(String title, String description) {
        String publicCode = UUID.randomUUID().toString();
        Analysis analysis = new Analysis(
            title,
            description,
            publicCode,
            LocalDateTime.now()
        );
        analysisRepository.persist(analysis);
        return analysis;
    }

    public List<Analysis> getAllAnalyses() {
        return analysisRepository.findAll();
    }

    @Transactional
    public void deleteAnalysis(Long id) {
        //Krajse vndar API caller ne ve ali je bilo res kaj izbrisano
        //analysisRepository.findById(id).ifPresent(analysisRepository::delete);

        Analysis analysis = analysisRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Analysis not found"));

        analysisRepository.delete(analysis);
    }
    
}
