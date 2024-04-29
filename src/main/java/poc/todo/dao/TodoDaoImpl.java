package poc.todo.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import poc.todo.model.Todo;

import java.util.List;

/**
 * Implementation of the TodoDao interface using JDBC operations for database interactions.
 */
@Service
public class TodoDaoImpl implements TodoDao {

    private static final Logger logger = LoggerFactory.getLogger(TodoDaoImpl.class);

    @Autowired
    private JdbcOperations jdbcOperations;

    /**
     * RowMapper for mapping rows from the database to Todo objects.
     */
    private final RowMapper<Todo> prodRowMapper = (rs, rowNum) -> {
        Todo todo = new Todo();
        todo.setId(rs.getLong("id"));
        todo.setDescription(rs.getString("description"));
        todo.setCompleted(rs.getBoolean("completed"));
        return todo;
    };

    /**
     * RowMapper for retrieving the maximum ID from the database.
     */
    private final RowMapper<Long> maxRowId = (rs, rowNum) -> rs.getLong("id");

    /**
     * Retrieves all Todo items from the database.
     *
     * @return a list of all Todo items.
     */
    @Override
    public List<Todo> findAll() {
        logger.info("Fetching all Todo items.");
        return jdbcOperations.query(Queries.QUERY_FETCH_ALL, prodRowMapper);
    }

    /**
     * Finds a specific Todo item by its ID.
     *
     * @param id the ID of the Todo item to find.
     * @return the Todo item, or null if not found.
     */
    @Override
    public Todo findById(long id) {
        logger.info("Fetching Todo item with ID: {}", id);
        try {
            return jdbcOperations.queryForObject(Queries.QUERY_FETCH_BY_ID, prodRowMapper, id);
        } catch (DataAccessException e) {
            logger.error("Error fetching Todo item with ID {}: {}", id, e.getMessage());
            return null; // Return null if not found or error occurred.
        }
    }

    /**
     * Saves a new Todo item to the database, with auto-incremented ID.
     *
     * @param todo the Todo item to save.
     * @return the saved Todo item with the new ID.
     */
    @Override
    public Todo save(Todo todo) {
        logger.info("Saving a new Todo item.");
        // Get the maximum ID
        List<Long> maxIds = getMaxTodoId();
        long maxId = maxIds.isEmpty() ? 0 : maxIds.get(0);

        // Increment the maximum ID for the next Todo
        long nextId = maxId + 1;
        todo.setId(nextId);

        // Insert the Todo with the next ID
        jdbcOperations.update(Queries.QUERY_INSERT_Todo, nextId, todo.getDescription(), todo.getCompleted());
        logger.info("Todo item saved with ID: {}", nextId);
        return todo;
    }

    /**
     * Updates an existing Todo item in the database.
     *
     * @param todo the Todo item with updated information.
     * @return the updated Todo item, or null if no item was updated.
     */
    @Override
    public Todo updateById(Todo todo) {
        logger.info("Updating Todo item with ID: {}", todo.getId());
        int rows = jdbcOperations.update(Queries.QUERY_PATCH_Todo, todo.getDescription(), todo.getCompleted(), todo.getId());
        if (rows == 1) {
            logger.info("Todo item updated with ID: {}", todo.getId());
            return todo;
        } else {
            logger.warn("Failed to update Todo item with ID: {}", todo.getId());
            return null; // Return null if update didn't occur.
        }
    }

    /**
     * Deletes a Todo item by its ID.
     *
     * @param id the ID of the Todo item to delete.
     */
    @Override
    public void deleteById(long id) {
        logger.info("Deleting Todo item with ID: {}", id);
        jdbcOperations.update(Queries.QUERY_DELETE_BY_ID, id);
        logger.info("Todo item with ID {} deleted.", id);
    }

    /**
     * Checks if a Todo item exists by its ID.
     *
     * @param id the ID to check.
     * @return true if the Todo item exists, false otherwise.
     */
    @Override
    public boolean existsById(long id) {
        logger.info("Checking if Todo item with ID {} exists.", id);
        Integer count = jdbcOperations.queryForObject(Queries.QUERY_COUNT_BY_ID, Integer.class, id);
        boolean exists = count != null && count > 0;
        if (exists) {
            logger.info("Todo item with ID {} exists.", id);
        } else {
            logger.warn("Todo item with ID {} does not exist.", id);
        }
        return exists;
    }

    /**
     * Retrieves the maximum ID assigned to a Todo item.
     *
     * @return a list containing the maximum ID, usually with one element.
     */
    @Override
    public List<Long> getMaxTodoId() {
        logger.info("Retrieving the maximum Todo ID.");
        return jdbcOperations.query(Queries.QUERY_MAX_Todo_ID, maxRowId);
    }

    /**
     * Defines SQL queries used by the TodoDaoImpl.
     */
    interface Queries {
        String QUERY_FETCH_ALL = "SELECT * FROM Todo"; // Fetch all Todo items.
        String QUERY_FETCH_BY_ID = "SELECT * FROM Todo WHERE ID = ?"; // Fetch a Todo by ID.
        String QUERY_DELETE_BY_ID = "DELETE FROM Todo WHERE ID = ?"; // Delete a Todo by ID.
        String QUERY_INSERT_Todo = "INSERT INTO Todo (ID, DESCRIPTION, COMPLETED) VALUES (?,?,?)"; // Insert a new Todo.
        String QUERY_PATCH_Todo = "UPDATE Todo SET DESCRIPTION = ?, COMPLETED = ? WHERE ID = ?"; // Update a Todo.
        String QUERY_MAX_Todo_ID = "SELECT MAX(ID) AS ID FROM Todo"; // Get the maximum ID.
        String QUERY_COUNT_BY_ID = "SELECT COUNT(*) FROM Todo WHERE ID = ?"; // Count Todo items by ID.
    }
}
