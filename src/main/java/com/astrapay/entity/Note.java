package com.astrapay.entity;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;
import java.time.Instant;


@Data
public class Note {

    private UUID id;

    @NotEmpty(message = "Title is required")
    private String title;

    @NotEmpty(message = "Content is required")
    private String content;

    private Instant createdDate = Instant.now();
    private Instant modifiedDate = Instant.now();

}
