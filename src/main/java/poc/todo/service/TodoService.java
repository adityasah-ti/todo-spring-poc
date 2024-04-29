package poc.todo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poc.todo.exception.MissingTodoDescriptionException;
import poc.todo.exception.NoTodoFoundException;
import poc.todo.model.Todo;
import poc.todo.repository.TodoRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service class for handling business logic related to Todo items.
 * Provides CRUD operations and additional business rules for Todo entities.
 */
@Service
public class TodoService {

    private static final Logger logger = LoggerFactory.getLogger(TodoService.class);

    private final TodoRepository todoRepository;

    /**
     * Constructor for TodoService, injecting the TodoRepository.
     *
     * @param todoRepository the repository for Todo items.
     */
    @Autowired
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
        logger.info("TodoService initialized with TodoRepository.");
    }

    /**
     * Retrieves all Todo items.
     *
     * @return a list of all Todo items from the repository.
     */
    public List<Todo> getAllTodos() {
        logger.info("Fetching all Todo items.");
        List<Todo> todos = todoRepository.findAll();
        if (todos.isEmpty()) {
            logger.warn("No Todo items found.");
            throw new NoTodoFoundException("No Todo items found.");
        }
        logger.info("Retrieved {} Todo items.", todos.size());
        return todos;
    }

    /**
     * Retrieves a specific Todo item by its ID.
     *
     * @param id the ID of the Todo item to retrieve.
     * @return the Todo item if found, or null if not found.
     */
    public Todo getTodoById(Long id) {
        logger.info("Fetching Todo item with ID: {}", id);
        Optional<Todo> optionalTodo = todoRepository.findById(id);
        if (optionalTodo.isPresent()) {
            logger.info("Found Todo item with ID: {}", id);
            return optionalTodo.get();
        } else {
            logger.warn("Todo item with ID {} not found.", id);
            throw new NoTodoFoundException("No Todo item found with ID " + id);
        }
    }

    /**
     * Creates a new Todo item.
     *
     * @param todo the Todo item to create.
     * @return the newly created Todo item.
     * @throws IllegalArgumentException if the description is null or empty.
     */
    public Todo createTodo(Todo todo) throws IllegalArgumentException {
        logger.info("Creating a new Todo item.");
        if (todo.getDescription() == null || todo.getDescription().isEmpty()) {
            logger.error("Failed to create Todo item: description is null or empty.");
            throw new MissingTodoDescriptionException("Failed to create Todo item: description is null or empty.");
        }

        if (todo.getCompleted() == null) {
            todo.setCompleted(false); // Default to false if not provided
        }

        Todo createdTodo = todoRepository.save(todo);
        logger.info("Created Todo item with ID: {}", createdTodo.getId());
        return createdTodo;
    }

    /**
     * Updates an existing Todo item with new data.
     *
     * @param id          the ID of the Todo item to update.
     * @param newTodoData the updated Todo data.
     * @return the updated Todo item, or null if the item doesn't exist.
     */
    public Todo updateTodo(Long id, Todo newTodoData) {
        logger.info("Updating Todo item with ID: {}", id);
        Optional<Todo> optionalTodo = todoRepository.findById(id);
        if (optionalTodo.isPresent()) {
            Todo todo = optionalTodo.get();

            if (newTodoData.getCompleted() != null) {
                todo.setCompleted(newTodoData.getCompleted());
            }

            if (newTodoData.getDescription() == null || newTodoData.getDescription().isEmpty()) {
                logger.error("Failed to update Todo item: description is empty.");
                throw new MissingTodoDescriptionException("Failed to update Todo item: description is null or empty.");
            }

            todo.setDescription(newTodoData.getDescription());
            Todo updatedTodo = todoRepository.save(todo);
            logger.info("Updated Todo item with ID: {}", updatedTodo.getId());
            return updatedTodo;
        } else {
            logger.warn("Failed to update Todo item: ID {} not found.", id);
            throw new NoTodoFoundException("No Todo item found with ID " + id);
        }
    }

    /**
     * Deletes a Todo item by its ID.
     *
     * @param id the ID of the Todo item to delete.
     * @return true if the Todo item was deleted, false if it did not exist.
     */
    public boolean deleteTodoById(Long id) {
        logger.info("Deleting Todo item with ID: {}", id);
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
            logger.info("Todo item with ID {} deleted.", id);
            return true;
        } else {
            logger.warn("Todo item with ID {} does not exist, cannot delete.", id);
            throw new NoTodoFoundException("No Todo item found with ID " + id);
        }
    }
}
