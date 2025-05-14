package com.arsheefy.task_manager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for creating a comment")
public class CommentRequestDto {

    @Schema(description = "Author of the comment", example = "John Doe", required = true)
    private String author;

    @Schema(description = "Content of the comment", example = "This task needs attention", required = true)
    private String content;
}