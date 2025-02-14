package com.astrapay.dto;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class NoteResponseDto {
    private UUID id;
    private String title;
    private String content;
    private Instant createdDate;
    private Instant modifiedDate;


}
