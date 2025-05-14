package com.arsheefy.task_manager.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response object containing comment information")
public class CommentResponseDto {

    @Schema(description = "Unique identifier of the comment", example = "1")
    private Long id;

    @Schema(description = "ID of the task this comment belongs to", example = "42")
    private Long taskId;

    @Schema(description = "Author of the comment", example = "John Doe")
    private String author;

    @Schema(description = "Content of the comment", example = "This task needs attention")
    private String content;

    @Schema(description = "Date and time when the comment was created", example = "2025-05-14T10:30:00")
    private LocalDateTime createdAt;
}