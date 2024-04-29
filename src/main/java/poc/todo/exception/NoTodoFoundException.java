package poc.todo.exception;

/**
 * Custom exception thrown when a Todo item is not found.
 */
public class NoTodoFoundException extends RuntimeException {

    /**
     * Constructs a new NoTodoFoundException with the specified detail message.
     *
     * @param message the detail message indicating why the exception was thrown.
     */
    public NoTodoFoundException(String message) {
        super(message); // Pass the message to the RuntimeException constructor
    }
}
