package com.arsheefy.task_manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arsheefy.task_manager.dto.TaskRequestDto;
import com.arsheefy.task_manager.dto.TaskStatusUpdateDto;
import com.arsheefy.task_manager.model.Task;
import com.arsheefy.task_manager.model.TaskStatus;
import com.arsheefy.task_manager.service.TaskService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Tasks", description = "Task management APIs")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Endpoint to create a new task
     * 
     * @param taskRequestDto The task details from the request
     * @return ResponseEntity containing the created task
     */
    @Operation(summary = "Create a new task", description = "Creates a new task with the provided information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task created successfully", content = @Content(schema = @Schema(implementation = Task.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    @PostMapping
    public ResponseEntity<Task> createTask(
            @Parameter(description = "Task details", required = true) @RequestBody TaskRequestDto taskRequestDto) {
        Task task = new Task();
        task.setTitle(taskRequestDto.getTitle());
        task.setDescription(taskRequestDto.getDescription());
        task.setAssignee(taskRequestDto.getAssignee());
        task.setDueDate(taskRequestDto.getDueDate());

        Task createdTask = taskService.createTask(task, taskRequestDto.getStatusId());
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    /**
     * Endpoint to get all tasks
     * 
     * @return ResponseEntity containing list of all tasks
     */
    @Operation(summary = "Get all tasks", description = "Retrieves a list of all tasks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved tasks", content = @Content(schema = @Schema(implementation = Task.class)))
    })
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    /**
     * Endpoint to get a task by ID
     * 
     * @param id The ID of the task to retrieve
     * @return ResponseEntity containing the task
     */
    @Operation(summary = "Get a task by ID", description = "Retrieves a specific task by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the task", content = @Content(schema = @Schema(implementation = Task.class))),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(
            @Parameter(description = "ID of the task to retrieve", required = true) @PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    /**
     * Endpoint to get the status of a task
     * 
     * @param id The ID of the task
     * @return ResponseEntity containing the task status
     */
    @Operation(summary = "Get task status", description = "Retrieves the status of a specific task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved task status", content = @Content(schema = @Schema(implementation = TaskStatus.class))),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @GetMapping("/{id}/status")
    public ResponseEntity<TaskStatus> getTaskStatus(
            @Parameter(description = "ID of the task", required = true) @PathVariable Long id) {
        TaskStatus status = taskService.getTaskStatus(id);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    /**
     * Endpoint to update the status of a task
     * 
     * @param id              The ID of the task
     * @param statusUpdateDto The new status details
     * @return ResponseEntity containing the updated task
     */
    @Operation(summary = "Update task status", description = "Updates the status of a specific task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task status updated successfully", content = @Content(schema = @Schema(implementation = Task.class))),
            @ApiResponse(responseCode = "404", description = "Task or status not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<Task> updateTaskStatus(
            @Parameter(description = "ID of the task", required = true) @PathVariable Long id,
            @Parameter(description = "New status details", required = true) @RequestBody TaskStatusUpdateDto statusUpdateDto) {

        Task updatedTask = taskService.updateTaskStatus(id, statusUpdateDto.getStatusId());
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }
}