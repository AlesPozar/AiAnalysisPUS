package com.ales.aianalysis.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "analysis_session")
public class AnalysisSession {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analysis_id", nullable = false)
    private Analysis analysis;

    @Column(name = "respondent")
    private String respondent;

    @Column(name = "completed_at", nullable = false)
    private LocalDateTime completedAt;

    public AnalysisSession(Analysis analysis, String respondent){
        this.analysis = analysis;
        this.respondent = respondent;
    }

    @PrePersist
    protected void onCreate(){
        this.completedAt = LocalDateTime.now();
    }
    

}
