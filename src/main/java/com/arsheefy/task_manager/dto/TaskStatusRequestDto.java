package com.arsheefy.task_manager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for creating a task status")
public class TaskStatusRequestDto {

    @Schema(description = "Name of the task status", example = "In Review", required = true)
    private String name;

    @Schema(description = "Description of what this status represents", example = "Task is currently being reviewed")
    private String description;

    @Schema(description = "Color code for UI representation", example = "#FFA500")
    private String color;
}