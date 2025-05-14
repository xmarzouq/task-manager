package com.arsheefy.task_manager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for updating a task's status")
public class TaskStatusUpdateDto {

    @Schema(description = "ID of the new task status", example = "2", required = true)
    private Long statusId;
}