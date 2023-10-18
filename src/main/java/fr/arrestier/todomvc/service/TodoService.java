package fr.arrestier.todomvc.service;

import fr.arrestier.todomvc.repository.TodoRepository;
import fr.arrestier.todomvc.service.exception.AlreadyExisting;
import fr.arrestier.todomvc.service.exception.NotFound;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TodoService {
    private final TodoRepository todoRepository;
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getAll() {
        var todos = todoRepository.findAll();
        return StreamSupport.stream(todos.spliterator(), false).collect(Collectors.toList());
    }

    public Todo create(String title) {
        Todo todoToCreate = new Todo(title);;

        try {
            return todoRepository.save(todoToCreate);
        } catch (RuntimeException e) {
            throw new AlreadyExisting(todoToCreate.title);
        }
    }

    public void deleteAll() {
        todoRepository.deleteAll();
    }

    public void deleteCompleted() {
        todoRepository.deleteCompleted();
    }

    public Optional<Todo> findById(String id) {
        return todoRepository.findById(id);
    }

    public Todo updateById(String id, String title, boolean completed, int order) {
        return todoRepository.findById(id)
                .map(todo -> {
                    todo.title = title;
                    todo.completed = completed;
                    todo.order = order;
                    try {
                        return todoRepository.save(todo);
                    } catch(RuntimeException e) {
                        throw new AlreadyExisting(title);
                    }
                }).orElseThrow(() -> new NotFound(id));
    }

    public void deleteById(String id) {
        todoRepository.deleteById(id);
    }
}
