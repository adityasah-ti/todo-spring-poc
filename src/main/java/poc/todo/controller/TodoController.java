package poc.todo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poc.todo.model.Todo;
import poc.todo.service.TodoServiceDao;

import java.util.List;

/**
 * Controller for handling HTTP requests related to "Todo" resources.
 */
@RestController
@RequestMapping("/todos")
public class TodoController {

//    TodoService using JPA Repository
//    @Autowired
//    private final TodoService todoService;

    // Initialize the logger for this class
    private static final Logger logger = LoggerFactory.getLogger(TodoController.class);

    // TodoService using DAO implementation
    @Autowired
    TodoServiceDao todoService;

    /**
     * Endpoint to check the status of the service.
     *
     * @return a ResponseEntity containing a simple "up" message, indicating the service is running.
     */
    @GetMapping("/info")
    public ResponseEntity<String> info() {
        logger.info("Service status checked.");
        return ResponseEntity.ok("up");
    }

    /**
     * Retrieves all Todo items.
     *
     * @return a ResponseEntity containing a list of all Todo items, or a "not found" response if the list is empty.
     */
    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos() {
        logger.info("Fetching all Todo items.");
        List<Todo> todos = todoService.getAllTodos();
        logger.info("Fetched {} Todo items.", todos.size());
        return ResponseEntity.ok(todos);
    }

    /**
     * Retrieves a specific Todo item by its ID.
     *
     * @param id the ID of the Todo item to retrieve.
     * @return a ResponseEntity containing the requested Todo item, or a "not found" response if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable("id") Long id) {
        logger.info("Fetching Todo item with ID: {}", id);
        Todo todo = todoService.getTodoById(id);
        return ResponseEntity.ok(todo);
    }

    /**
     * Creates a new Todo item.
     *
     * @param todo the Todo item to create, passed in the request body.
     * @return a ResponseEntity containing the created Todo item with a "created" status, or a "bad request" response if there's an error.
     */
    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
        logger.info("Creating a new Todo item.");
        Todo createdTodo = todoService.createTodo(todo);
        logger.info("Todo item created with ID: {}", createdTodo.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo);
    }

    /**
     * Updates an existing Todo item by its ID.
     *
     * @param id   the ID of the Todo item to update.
     * @param todo the updated Todo information.
     * @return a ResponseEntity containing the updated Todo item, or a "not found" response if the item doesn't exist.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable("id") Long id, @RequestBody Todo todo) {
        logger.info("Updating Todo item with ID: {}", id);
        Todo updatedTodo = todoService.updateTodo(id, todo);
        return ResponseEntity.ok(updatedTodo);
    }

    /**
     * Deletes a Todo item by its ID.
     *
     * @param id the ID of the Todo item to delete.
     * @return a ResponseEntity indicating success with "no content", or "not found" if the item doesn't exist.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable("id") Long id) {
        logger.info("Deleting Todo item with ID: {}", id);
        todoService.deleteTodoById(id);
        logger.info("Todo item with ID {} deleted.", id);
        return ResponseEntity.noContent().build();
    }
}
