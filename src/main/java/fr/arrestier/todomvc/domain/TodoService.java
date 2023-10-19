package fr.arrestier.todomvc.domain;

import fr.arrestier.todomvc.infrastructure.TodoRepository;
import fr.arrestier.todomvc.domain.exception.AlreadyExisting;
import fr.arrestier.todomvc.domain.exception.NotFound;
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

    public Todo create(String title) throws AlreadyExisting {
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
        todoRepository.deleteByCompleted(true);
    }

    public Optional<Todo> findById(String id) {
        return todoRepository.findById(id);
    }

    public Todo updateById(String id, String title, boolean completed, int order) throws AlreadyExisting, NotFound {
        if(todoRepository.findByTitle(title).isPresent()) {
            throw new AlreadyExisting(title);
        }

        return todoRepository.findById(id)
                .map(todo -> {
                    todo.title = title;
                    todo.completed = completed;
                    todo.order = order;
                    return todoRepository.save(todo);
                }).orElseThrow(() -> new NotFound(id));
    }

    public void deleteById(String id) throws NotFound {
        if(todoRepository.findById(id).isEmpty()) {
            throw new NotFound(id);
        }
        todoRepository.deleteById(id);
    }
}
