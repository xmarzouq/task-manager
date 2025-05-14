package com.arsheefy.task_manager.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class TaskManagerException extends RuntimeException {
    private String message;
    private String errorCode;

    public TaskManagerException(String message) {
        super(message);
        this.message = message;
    }
}
