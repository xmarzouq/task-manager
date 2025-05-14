package com.arsheefy.task_manager.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arsheefy.task_manager.dto.CommentRequestDto;
import com.arsheefy.task_manager.dto.CommentResponseDto;
import com.arsheefy.task_manager.exception.TaskManagerException;
import com.arsheefy.task_manager.model.Comment;
import com.arsheefy.task_manager.model.Task;
import com.arsheefy.task_manager.repository.CommentRepository;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final TaskService taskService;

    @Autowired
    public CommentService(CommentRepository commentRepository, TaskService taskService) {
        this.commentRepository = commentRepository;
        this.taskService = taskService;
    }

    /**
     * Add a comment to a task
     * 
     * @param taskId     The ID of the task to comment on
     * @param commentDto The comment data
     * @return The saved comment as a response DTO
     */
    public CommentResponseDto addComment(Long taskId, CommentRequestDto commentDto) {
        // Get the task
        Task task = taskService.getTaskById(taskId);

        // Create and populate the comment
        Comment comment = new Comment();
        comment.setTask(task);
        comment.setAuthor(commentDto.getAuthor());
        comment.setContent(commentDto.getContent());

        // Save the comment
        Comment savedComment = commentRepository.save(comment);

        // Convert to response DTO
        return convertToResponseDto(savedComment);
    }

    /**
     * Get all comments for a task
     * 
     * @param taskId The ID of the task
     * @return List of comments for the task
     */
    public List<CommentResponseDto> getCommentsForTask(Long taskId) {
        // Check if task exists
        if (!taskService.existsById(taskId)) {
            throw new TaskManagerException("Task not found with id: " + taskId, "TASK_NOT_FOUND");
        }

        // Get comments and convert to DTOs
        return commentRepository.findByTaskIdOrderByCreatedAtDesc(taskId)
                .stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Convert a Comment entity to a CommentResponseDto
     */
    private CommentResponseDto convertToResponseDto(Comment comment) {
        CommentResponseDto dto = new CommentResponseDto();
        dto.setId(comment.getId());
        dto.setTaskId(comment.getTask().getId());
        dto.setAuthor(comment.getAuthor());
        dto.setContent(comment.getContent());
        dto.setCreatedAt(comment.getCreatedAt());
        return dto;
    }
}