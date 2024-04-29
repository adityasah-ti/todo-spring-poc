package poc.todo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Represents a Todo item in the system.
 */
@Entity
public class Todo {

    @GeneratedValue(strategy = GenerationType.AUTO) // ID is auto-generated
    @Id
    Long id; // The unique identifier for a Todo item

    /**
     * Gets the ID of the Todo item.
     *
     * @return the ID of the Todo item.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the Todo item.
     *
     * @param id the ID to set for the Todo item.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the description of the Todo item.
     *
     * @return the description of the Todo item.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the Todo item.
     *
     * @param description the description to set for the Todo item.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets whether the Todo item is completed.
     *
     * @return true if the Todo item is completed, false otherwise.
     */
    public Boolean getCompleted() {
        return completed;
    }

    /**
     * Sets whether the Todo item is completed.
     *
     * @param completed true if the Todo item is completed, false otherwise.
     */
    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    String description; // The description of the Todo item
    Boolean completed;  // Whether the Todo item is completed
}
