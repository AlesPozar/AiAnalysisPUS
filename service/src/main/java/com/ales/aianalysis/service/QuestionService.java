package com.ales.aianalysis.service;

import com.ales.aianalysis.entity.Analysis;
import com.ales.aianalysis.entity.Question;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class QuestionService {
    @Inject
    QuestionRepository questionRepository;

    @Inject
    AnalysisRepository analysisRepository;
    
    @Transactional
    public Question createQuestion(Long analysisId, String questionText, Integer position){
        Analysis analysis = analysisRepository.findById(analysisId)
                .orElseThrow(() -> new IllegalArgumentException("Analysis not found"));

        if(questionRepository.findByAnalysisIdAndPosition(analysisId, position).isPresent()){
            throw new IllegalArgumentException("Question position already exists for this analysis");
        }

        Question question = new Question(
                analysis,
                questionText,
                position
        );

        questionRepository.persist(question);

        return question;
    }

    public List<Question> getQuestionsByAnalysisId(Long analysisId) {
        return questionRepository.findByAnalysisId(analysisId);
    }

    public Question getQuestionByAnalysisIDAndQuestionId(Long analysisId, Long questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new IllegalArgumentException("Question not found"));

        if(!question.getAnalysis().getId().equals(analysisId)) {
            throw new IllegalArgumentException("Question does not belong to this analysis");
        }

        return question;
    }
}