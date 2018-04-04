package org.linagora.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan({"org.linagora.service.rest"})
public class ApplicationWorkflowReader {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationWorkflowReader.class, args);
    }
}
