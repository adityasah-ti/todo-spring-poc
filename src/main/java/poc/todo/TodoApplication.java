package poc.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main entry point for the Todo application.
 * This class is responsible for starting the Spring Boot application.
 */
@SpringBootApplication
public class TodoApplication {

    /**
     * The main method that starts the Spring Boot application.
     *
     * @param args command-line arguments passed at startup.
     */
    public static void main(String[] args) {
        SpringApplication.run(TodoApplication.class, args);
    }
}
