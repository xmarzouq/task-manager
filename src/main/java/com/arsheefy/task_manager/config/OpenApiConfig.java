package com.arsheefy.task_manager.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

        @Value("${server.servlet.context-path:}")
        private String contextPath;

        @Bean
        public OpenAPI taskManagerOpenAPI() {
                Server server = new Server()
                                .url(contextPath)
                                .description("Current Server");

                Contact contact = new Contact()
                                .name("Task Manager Team")
                                .email("support@arsheefy.com");

                Info info = new Info()
                                .title("Task Manager API")
                                .version("1.0.0")
                                .description("RESTful API for managing tasks, statuses, and comments")
                                .contact(contact)
                                .license(new License().name("Apache 2.0")
                                                .url("https://www.apache.org/licenses/LICENSE-2.0"));

                return new OpenAPI()
                                .info(info)
                                .servers(List.of(server));
        }
}