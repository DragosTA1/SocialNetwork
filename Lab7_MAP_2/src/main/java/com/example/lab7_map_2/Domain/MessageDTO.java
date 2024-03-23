package com.example.lab7_map_2.Domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class MessageDTO {
    private Long from;
    private Long to;
    private String message;
    private LocalDate data;
    private Long repliedMessage;

    public MessageDTO(Long from, Long to, String message, LocalDate data) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.data = data;
        this.repliedMessage = null;
    }

    public MessageDTO(Long from, Long to, String message, LocalDate data, Long idReplied) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.data = data;
        this.repliedMessage = idReplied;
    }

    public Long getFrom() {
        return from;
    }

    public Long getTo() {
        return to;
    }

    public String getMessage() {
        return message;
    }

    public LocalDate getData() {
        return data;
    }

    public Long getRepliedMessage() {
        return repliedMessage;
    }
}
