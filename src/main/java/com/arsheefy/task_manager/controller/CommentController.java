package com.arsheefy.task_manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arsheefy.task_manager.dto.CommentRequestDto;
import com.arsheefy.task_manager.dto.CommentResponseDto;
import com.arsheefy.task_manager.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/tasks/{taskId}/comments")
@Tag(name = "Comments", description = "Comment management APIs")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(summary = "Add a comment to a task", description = "Creates a new comment for a specific task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment created successfully", content = @Content(schema = @Schema(implementation = CommentResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Task not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    @PostMapping
    public ResponseEntity<CommentResponseDto> addComment(
            @Parameter(description = "ID of the task to comment on", required = true) @PathVariable Long taskId,
            @Parameter(description = "Comment details", required = true) @RequestBody CommentRequestDto commentDto) {

        CommentResponseDto createdComment = commentService.addComment(taskId, commentDto);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all comments for a task", description = "Retrieves all comments for a specific task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved comments"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getCommentsForTask(
            @Parameter(description = "ID of the task", required = true) @PathVariable Long taskId) {

        List<CommentResponseDto> comments = commentService.getCommentsForTask(taskId);
        return ResponseEntity.ok(comments);
    }
}