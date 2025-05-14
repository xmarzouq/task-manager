package com.arsheefy.task_manager.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entity representing a task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the task", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Title of the task", example = "Implement API Documentation", required = true)
    private String title;

    @Column(columnDefinition = "TEXT")
    @Schema(description = "Detailed description of the task", example = "Add Swagger documentation to all endpoints")
    private String description;

    @Schema(description = "Person assigned to the task", example = "Mohamed Ali")
    private String assignee;

    @Schema(description = "Due date for the task", example = "2025-06-01")
    private LocalDate dueDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id")
    @Schema(description = "Status of the task")
    private TaskStatus status;

    @Column(updatable = false)
    @Schema(description = "Date and time when the task was created", example = "2025-05-14T10:30:00")
    private LocalDateTime createdAt;

    // Pre-persist hook to set createdAt field before saving to DB
    @jakarta.persistence.PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}