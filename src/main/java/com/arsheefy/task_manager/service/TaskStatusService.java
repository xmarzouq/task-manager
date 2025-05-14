package com.arsheefy.task_manager.service;

import java.util.List;
// import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arsheefy.task_manager.exception.TaskManagerException;
import com.arsheefy.task_manager.model.TaskStatus;
import com.arsheefy.task_manager.repository.TaskStatusRepository;

@Service
public class TaskStatusService {

    private final TaskStatusRepository taskStatusRepository;

    @Autowired
    public TaskStatusService(TaskStatusRepository taskStatusRepository) {
        this.taskStatusRepository = taskStatusRepository;
    }

    /**
     * Creates a new task status
     * 
     * @param taskStatus The task status to be created
     * @return The created task status with generated ID
     */
    public TaskStatus createTaskStatus(TaskStatus taskStatus) {
        // Check if status with the same name already exists
        if (taskStatusRepository.existsByName(taskStatus.getName())) {
            throw new TaskManagerException("Task status with name '" + taskStatus.getName() + "' already exists",
                    "DUPLICATE_STATUS");
        }

        // Save the task status
        return taskStatusRepository.save(taskStatus);
    }

    /**
     * Retrieves all task statuses
     * 
     * @return List of all task statuses
     */
    public List<TaskStatus> getAllTaskStatuses() {
        return taskStatusRepository.findAll();
    }

    /**
     * Retrieves a task status by its ID
     * 
     * @param id The ID of the task status to retrieve
     * @return The task status if found
     */
    public TaskStatus getTaskStatusById(Long id) {
        return taskStatusRepository.findById(id)
                .orElseThrow(
                        () -> new TaskManagerException("Task status not found with id: " + id, "STATUS_NOT_FOUND"));
    }

    /**
     * Initializes default task statuses if they don't exist
     */
    public void initializeDefaultStatuses() {
        // Common default statuses
        createIfNotExists("To Do", "Tasks that are not yet started", "#808080");
        createIfNotExists("In Progress", "Tasks that are currently being worked on", "#FFA500");
        createIfNotExists("Done", "Tasks that are completed", "#008000");
    }

    /**
     * Helper method to create a status if it doesn't exist
     */
    private void createIfNotExists(String name, String description, String color) {
        if (!taskStatusRepository.existsByName(name)) {
            TaskStatus status = new TaskStatus();
            status.setName(name);
            status.setDescription(description);
            status.setColor(color);
            taskStatusRepository.save(status);
        }
    }
}
