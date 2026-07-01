package com.ales.aianalysis.api.dto;

import com.ales.aianalysis.entity.ResponseMessage;

import java.time.LocalDateTime;

public class ResponseMessageResponse {
    public Long id;
    public String sender;
    public String messageText;
    public Integer position;
    public LocalDateTime createdAt;

    public ResponseMessageResponse(Long id, String sender, String messageText, Integer position, LocalDateTime createdAt) {
        this.id = id;
        this.sender = sender;
        this.messageText = messageText;
        this.position = position;
        this.createdAt = createdAt;
    }

    public static ResponseMessageResponse fromEntity(ResponseMessage entity) {
        return new ResponseMessageResponse(
                entity.getId(),
                entity.getSender().name(),
                entity.getMessageText(),
                entity.getPosition(),
                entity.getCreatedAt()
        );
    }
}
