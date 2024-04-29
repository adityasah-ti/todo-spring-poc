package poc.todo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import poc.todo.exception.MissingTodoDescriptionException;
import poc.todo.exception.NoTodoFoundException;
import poc.todo.model.CustomTodoError;

/**
 * Global exception handler for REST controllers.
 * This class handles exceptions thrown by other controllers and returns appropriate HTTP responses.
 */
@RestController
@ControllerAdvice
public class RestExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    /**
     * Handles exceptions when a Todo item is not found.
     *
     * @param e the exception indicating a Todo item wasn't found.
     * @return a ResponseEntity with a custom error message and HTTP status "NOT FOUND".
     */
    @ExceptionHandler(value = NoTodoFoundException.class)
    public ResponseEntity<CustomTodoError> handleNoTodoFoundException(NoTodoFoundException e) {
        logger.warn("Todo not found: {}", e.getMessage());
        CustomTodoError error = new CustomTodoError(e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles exceptions when a Todo item lacks a description.
     *
     * @param e the exception indicating a missing description.
     * @return a ResponseEntity with a custom error message and HTTP status "BAD REQUEST".
     */
    @ExceptionHandler(value = MissingTodoDescriptionException.class)
    public ResponseEntity<CustomTodoError> handleMissingTodoDescriptionException(MissingTodoDescriptionException e) {
        logger.error("Missing description in Todo: {}", e.getMessage());
        CustomTodoError error = new CustomTodoError(e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
