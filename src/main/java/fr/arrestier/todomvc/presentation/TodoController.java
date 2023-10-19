package fr.arrestier.todomvc.presentation;

import fr.arrestier.todomvc.domain.Todo;
import fr.arrestier.todomvc.domain.TodoService;
import fr.arrestier.todomvc.domain.exception.AlreadyExisting;
import fr.arrestier.todomvc.domain.exception.InvalidData;
import fr.arrestier.todomvc.domain.exception.NotFound;
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
    public ResponseEntity<TodoResponse> createTodo(@Valid @RequestBody TodoCreationRequest todoToCreate) throws AlreadyExisting {
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
    public TodoResponse findById(@PathVariable("id") String id) throws NotFound {
        return todoService.findById(id).map(TodoResponse::fromTodo).orElseThrow(() -> new NotFound(id));
    }

    @PutMapping("/{id}")
    @ResponseBody
    public TodoResponse updateById(@PathVariable("id") String id, @Valid @RequestBody TodoUpdateRequest todoToUpdate) throws AlreadyExisting, NotFound, InvalidData {
        var updatedTodo = todoService.updateById(id, todoToUpdate.title, todoToUpdate.completed, todoToUpdate.order);
        return TodoResponse.fromTodo(updatedTodo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") String id) throws NotFound {
        todoService.deleteById(id);
    }
}
