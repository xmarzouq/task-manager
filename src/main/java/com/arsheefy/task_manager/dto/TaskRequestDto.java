package com.arsheefy.task_manager.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for creating a task")
public class TaskRequestDto {

    @Schema(description = "Title of the task", example = "Implement API Documentation", required = true)
    private String title;

    @Schema(description = "Detailed description of the task", example = "Add Swagger documentation to all REST endpoints")
    private String description;

    @Schema(description = "Person assigned to the task", example = "John Doe")
    private String assignee;

    @Schema(description = "Due date for the task", example = "2025-06-01")
    private LocalDate dueDate;

    @Schema(description = "ID of the task status", example = "1")
    private Long statusId;
}