package fr.arrestier.todomvc.controller;

import fr.arrestier.todomvc.service.Todo;
import fr.arrestier.todomvc.service.TodoService;
import fr.arrestier.todomvc.service.exception.NotFound;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    @ResponseBody
    public List<TodoResponse> getAllTodos() {
        return todoService.getAll().stream().map(TodoResponse::fromTodo).collect(Collectors.toList());
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<TodoResponse> createTodo(@Valid @RequestBody TodoCreationRequest todoToCreate) {
        Todo createdTodo = todoService.create(todoToCreate.title);

        return ResponseEntity
                .created(URI.create("http://localhost:8080/todos/"+createdTodo.getId()))
                .body(TodoResponse.fromTodo(createdTodo));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll() {
        todoService.deleteAll();
    }

    @DeleteMapping("/completed")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompleted() {
        todoService.deleteCompleted();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Todo findById(@PathVariable("id") String id) {
        return todoService.findById(id).orElseThrow(() -> new NotFound(id));
    }

    @PutMapping("/{id}")
    @ResponseBody
    public Todo updateById(@PathVariable("id") String id, @Valid @RequestBody TodoUpdateRequest updatedTodo) {
        return todoService.updateById(id, updatedTodo.title, updatedTodo.completed, updatedTodo.order);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") String id) {
        todoService.findById(id).ifPresentOrElse(
                todo -> todoService.deleteById(todo.getId()),
                () -> {
                    throw new NotFound(id);
                });
    }
}
