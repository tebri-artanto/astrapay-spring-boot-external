package com.astrapay.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoteDto {
    @Valid

    @NotBlank(message = "Title is required")
    @NotNull(message = "Title is required")
    private String title;

    @NotEmpty(message = "Content is required")
    private String content;


}
