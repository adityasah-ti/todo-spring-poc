package poc.todo.model;

import java.time.LocalDateTime;

/**
 * Represents a custom error message for the Todo application, with a timestamp.
 */
public class CustomTodoError {

    private String errorMessage;  // The error message to display or log
    private LocalDateTime errorTime;  // The time when the error occurred

    /**
     * Default constructor for CustomTodoError.
     * Initializes an empty error message and timestamp.
     */
    public CustomTodoError() {
    }

    /**
     * Constructor for CustomTodoError with a specified error message and timestamp.
     *
     * @param errorMessage the error message.
     * @param errorTime the time when the error occurred.
     */
    public CustomTodoError(String errorMessage, LocalDateTime errorTime) {
        this.errorMessage = errorMessage;
        this.errorTime = errorTime;
    }

    /**
     * Constructor for CustomTodoError with a specified error message.
     * The timestamp is set to the current time.
     *
     * @param errorMessage the error message.
     */
    public CustomTodoError(String errorMessage) {
        this.errorMessage = errorMessage;
        this.errorTime = LocalDateTime.now();  // Set to current time
    }

    /**
     * Gets the error message.
     *
     * @return the error message.
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the error message.
     *
     * @param errorMessage the error message to set.
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Gets the time when the error occurred.
     *
     * @return the error time.
     */
    public LocalDateTime getErrorTime() {
        return errorTime;
    }

    /**
     * Sets the time when the error occurred.
     *
     * @param errorTime the error time to set.
     */
    public void setErrorTime(LocalDateTime errorTime) {
        this.errorTime = errorTime;
    }
}
