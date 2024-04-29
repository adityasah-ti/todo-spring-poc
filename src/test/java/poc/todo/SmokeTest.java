package poc.todo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import poc.todo.controller.TodoController;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class SmokeTest {
    @Autowired
    private TodoController controller;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }
}
