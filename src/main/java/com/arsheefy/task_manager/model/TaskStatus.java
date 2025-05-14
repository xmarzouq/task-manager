package com.arsheefy.task_manager.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "task_statuses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entity representing a task status")
public class TaskStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the task status", example = "1")
    private Long id;

    @Column(nullable = false, unique = true)
    @Schema(description = "Name of the task status", example = "In Progress")
    private String name;

    @Column
    @Schema(description = "Description of what this status represents", example = "Task is currently being worked on")
    private String description;

    @Column
    @Schema(description = "Color code for UI representation", example = "#0000FF")
    private String color;
}