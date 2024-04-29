package poc.todo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import poc.todo.controller.TodoController;
import poc.todo.model.Todo;
import poc.todo.service.TodoService;
import poc.todo.service.TodoServiceDao;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TodoController.class)
public class TestingWebApplicationTest {

    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    private TodoServiceDao service;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllSuccess() throws Exception {
        List<Todo> todos = new ArrayList<>();
        Todo todo = new Todo();
        todo.setId(1L);
        todo.setDescription("MockTodoTest");
        todo.setCompleted(true);
        todos.add(todo);
        todo = new Todo();
        todo.setId(2L);
        todo.setDescription("MockTodoTest2");
        todo.setCompleted(false);
        todos.add(todo);
        when(service.getAllTodos()).thenReturn(todos);
        this.mockMvc.perform(get("/todos"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(toJson(todos)));
    }

    @Test
    void testGetAllFail() throws Exception {
        List<Todo> todos = new ArrayList<>();
        when(service.getAllTodos()).thenReturn(todos);
        this.mockMvc.perform(get("/todos"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetByIdSuccess() throws Exception {
        Todo todo = new Todo();
        todo.setId(1L);
        todo.setDescription("MockTodoTest");
        todo.setCompleted(true);
        when(service.getTodoById(1L)).thenReturn(todo);
        this.mockMvc.perform(get("/todos/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(toJson(todo)));
    }

    @Test
    void testGetByIdFail() throws Exception {
        when(service.getTodoById(1L)).thenReturn(null);
        this.mockMvc.perform(get("/todos/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteByIdSuccess() throws Exception {
        when(service.deleteTodoById(1L)).thenReturn(true);
        this.mockMvc.perform(delete("/todos/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteByIdFail() throws Exception {
        when(service.deleteTodoById(1L)).thenReturn(false);
        this.mockMvc.perform(delete("/todos/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void testPostSuccess() throws Exception {
        Todo todo = new Todo();
        todo.setId(1L);
        todo.setDescription("MockTodoTest");
        todo.setCompleted(true);
        when(service.createTodo(todo)).thenReturn(todo);
        this.mockMvc.perform(post("/todos")
                        .content(toJson(todo))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(toJson(todo)));
    }

    @Test
    void testPostFail() throws Exception {
        Todo todo = new Todo();
        todo.setId(1L);
        when(service.createTodo(todo)).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> service.createTodo(todo));
        this.mockMvc.perform(post("/todos")
                        .content(toJson(todo))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testPatchSuccess() throws Exception {
        Todo todo = new Todo();
        todo.setId(1L);
        todo.setDescription("MockTodoTest");
        todo.setCompleted(true);
        when(service.updateTodo(1L, todo)).thenReturn(todo);
        this.mockMvc.perform(patch("/todos/1")
                        .content(toJson(todo))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(toJson(todo)));
    }

    @Test
    void testPatchFail() throws Exception {
        Todo todo = new Todo();
        todo.setId(1L);
        todo.setDescription("MockTodoTest");
        todo.setCompleted(true);
        when(service.updateTodo(1L, todo)).thenReturn(null);
        this.mockMvc.perform(patch("/todos/1")
                        .content(toJson(todo))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private String toJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}