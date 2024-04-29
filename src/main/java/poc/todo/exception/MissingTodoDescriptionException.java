package poc.todo.exception;

/**
 * Custom exception thrown when a required description is missing from a Todo item.
 * Extends IllegalArgumentException to indicate a problem with invalid input.
 */
public class MissingTodoDescriptionException extends IllegalArgumentException {

    /**
     * Constructs a new MissingTodoDescriptionException with the specified detail message.
     *
     * @param message the detail message indicating why the exception was thrown.
     */
    public MissingTodoDescriptionException(String message) {
        super(message); // Pass the message to the IllegalArgumentException constructor
    }
}
