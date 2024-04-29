package poc.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poc.todo.model.Todo;

/**
 * Repository interface for managing Todo entities.
 * Extends JpaRepository to provide CRUD operations and more.
 */
@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    // This interface inherits common JPA repository operations for Todo entities,
    // including methods for CRUD operations, pagination, and querying.
}
