package org.example.springnewbie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;

// annotation - Spring Boot Entry
@SpringBootApplication(exclude = { JacksonAutoConfiguration.class })
public class SpringNewbieApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringNewbieApplication.class, args);
    }
}
