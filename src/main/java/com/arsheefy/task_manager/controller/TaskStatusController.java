package com.arsheefy.task_manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arsheefy.task_manager.dto.TaskStatusRequestDto;
import com.arsheefy.task_manager.model.TaskStatus;
import com.arsheefy.task_manager.service.TaskStatusService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/task-statuses")
@Tag(name = "Task Statuses", description = "Task status management APIs")
public class TaskStatusController {

    private final TaskStatusService taskStatusService;

    @Autowired
    public TaskStatusController(TaskStatusService taskStatusService) {
        this.taskStatusService = taskStatusService;
    }

    /**
     * Endpoint to create a new task status
     * 
     * @param taskStatusRequestDto The task status details from the request
     * @return ResponseEntity containing the created task status
     */
    @Operation(summary = "Create a new task status", description = "Creates a new task status with the provided information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task status created successfully", content = @Content(schema = @Schema(implementation = TaskStatus.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "409", description = "Task status with the same name already exists")
    })
    @PostMapping
    public ResponseEntity<TaskStatus> createTaskStatus(
            @Parameter(description = "Task status details", required = true) @RequestBody TaskStatusRequestDto taskStatusRequestDto) {
        TaskStatus taskStatus = new TaskStatus();
        taskStatus.setName(taskStatusRequestDto.getName());
        taskStatus.setDescription(taskStatusRequestDto.getDescription());
        taskStatus.setColor(taskStatusRequestDto.getColor());

        TaskStatus createdTaskStatus = taskStatusService.createTaskStatus(taskStatus);
        return new ResponseEntity<>(createdTaskStatus, HttpStatus.CREATED);
    }

    /**
     * Endpoint to get all task statuses
     * 
     * @return ResponseEntity containing list of all task statuses
     */
    @Operation(summary = "Get all task statuses", description = "Retrieves a list of all task statuses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved task statuses", content = @Content(schema = @Schema(implementation = TaskStatus.class)))
    })
    @GetMapping
    public ResponseEntity<List<TaskStatus>> getAllTaskStatuses() {
        List<TaskStatus> taskStatuses = taskStatusService.getAllTaskStatuses();
        return new ResponseEntity<>(taskStatuses, HttpStatus.OK);
    }
}