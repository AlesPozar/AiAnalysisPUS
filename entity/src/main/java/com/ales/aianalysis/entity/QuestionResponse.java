package com.ales.aianalysis.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


// vsebuje odgovore in podvprasanja na posamezno glavno vprasanje

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "question_response",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_question_response_session_question",
                        columnNames = {"session_id", "question_id"}
                )
        }
)
public class QuestionResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private AnalysisSession session;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    public QuestionResponse(AnalysisSession session, Question question) {
        this.session = session;
        this.question = question;
    }
}
