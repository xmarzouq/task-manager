package com.arsheefy.task_manager;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.arsheefy.task_manager.service.TaskStatusService;

@SpringBootApplication
public class TaskManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagerApplication.class, args);
	}

	/**
	 * Initialize default task statuses when the application starts
	 */
	@Bean
	public CommandLineRunner init(TaskStatusService taskStatusService) {
		return args -> {
			taskStatusService.initializeDefaultStatuses();
		};
	}
}
