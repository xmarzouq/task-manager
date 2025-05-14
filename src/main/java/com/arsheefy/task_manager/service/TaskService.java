package com.arsheefy.task_manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arsheefy.task_manager.exception.TaskManagerException;
import com.arsheefy.task_manager.model.Task;
import com.arsheefy.task_manager.model.TaskStatus;
import com.arsheefy.task_manager.repository.TaskRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskStatusService taskStatusService;

    @Autowired
    public TaskService(TaskRepository taskRepository, TaskStatusService taskStatusService) {
        this.taskRepository = taskRepository;
        this.taskStatusService = taskStatusService;
    }

    /**
     * Creates a new task
     * 
     * @param task     The task to be created
     * @param statusId The ID of the status to be associated with the task
     * @return The created task with generated ID
     */
    public Task createTask(Task task, Long statusId) {
        // Set status if provided
        if (statusId != null) {
            TaskStatus status = taskStatusService.getTaskStatusById(statusId);
            task.setStatus(status);
        }

        // Set created time
        task.prePersist();

        // Save the task
        return taskRepository.save(task);
    }

    /**
     * Retrieves all tasks
     * 
     * @return List of all tasks
     */
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    /**
     * Get a task by its ID
     * 
     * @param id The ID of the task to retrieve
     * @return The task if found
     * @throws TaskManagerException if task is not found
     */
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskManagerException("Task not found with id: " + id, "TASK_NOT_FOUND"));
    }

    /**
     * Get the status of a task
     * 
     * @param taskId The ID of the task
     * @return The task status
     * @throws TaskManagerException if task is not found
     */
    public TaskStatus getTaskStatus(Long taskId) {
        Task task = getTaskById(taskId);
        return task.getStatus();
    }

    /**
     * Update the status of a task
     * 
     * @param taskId   The ID of the task to update
     * @param statusId The ID of the new status
     * @return The updated task
     * @throws TaskManagerException if task or status is not found
     */
    public Task updateTaskStatus(Long taskId, Long statusId) {
        // Find the task
        Task task = getTaskById(taskId);

        // Find the status
        TaskStatus status = taskStatusService.getTaskStatusById(statusId);

        // Update and save
        task.setStatus(status);
        return taskRepository.save(task);
    }

    /**
     * Check if a task exists by ID
     * 
     * @param id The ID to check
     * @return true if the task exists, false otherwise
     */
    public boolean existsById(Long id) {
        return taskRepository.existsById(id);
    }
}
