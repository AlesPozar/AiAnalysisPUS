package com.ales.aianalysis.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "response_message",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_response_message_question_response_position",
                        columnNames = {"question_response_id", "position"}
                )
        }
)
public class ResponseMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_response_id", nullable = false)
    private QuestionResponse questionResponse;

    @Enumerated(EnumType.STRING)
    @Column(name = "sender", nullable = false)
    private MessageSender sender;

    @Column(name = "message_text", nullable = false)
    private String messageText;

    @Column(name = "position", nullable = false)
    private Integer position;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public ResponseMessage(
            QuestionResponse questionResponse,
            MessageSender sender,
            String messageText,
            Integer position
    ) {
        this.questionResponse = questionResponse;
        this.sender = sender;
        this.messageText = messageText;
        this.position = position;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
