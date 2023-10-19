package fr.arrestier.todomvc.presentation;

import fr.arrestier.todomvc.domain.Todo;
import fr.arrestier.todomvc.domain.TodoService;
import fr.arrestier.todomvc.domain.exception.AlreadyExisting;
import fr.arrestier.todomvc.domain.exception.InvalidData;
import fr.arrestier.todomvc.domain.exception.NotFound;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService, HttpServletRequest httpServletRequest) {
        this.todoService = todoService;
    }

    private TodoResponse getTodoAsResponse(Todo todo) {
        return new TodoResponse(todo, ServletUriComponentsBuilder.fromCurrentContextPath().build().toString());
    }

    @GetMapping
    @ResponseBody
    public List<TodoResponse> getAllTodos() {
        return todoService.getAll().stream().map(this::getTodoAsResponse).collect(Collectors.toList());
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<TodoResponse> createTodo(@Valid @RequestBody TodoCreationRequest todoToCreate) throws AlreadyExisting {
        Todo createdTodo = todoService.create(todoToCreate.title);

        TodoResponse response = getTodoAsResponse(createdTodo);
        return ResponseEntity
                .created(URI.create(response.url()))
                .body(response);
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
    public TodoResponse findById(@PathVariable("id") String id) throws NotFound {
        return todoService.findById(id).map(this::getTodoAsResponse).orElseThrow(() -> new NotFound(id));
    }

    @PutMapping("/{id}")
    @ResponseBody
    public TodoResponse updateById(@PathVariable("id") String id, @Valid @RequestBody TodoUpdateRequest todoToUpdate) throws AlreadyExisting, NotFound, InvalidData {
        var updatedTodo = todoService.updateById(id, todoToUpdate.title, todoToUpdate.completed, todoToUpdate.order);
        return getTodoAsResponse(updatedTodo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") String id) throws NotFound {
        todoService.deleteById(id);
    }
}
