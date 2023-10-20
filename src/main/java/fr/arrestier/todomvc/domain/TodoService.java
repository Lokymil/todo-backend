package fr.arrestier.todomvc.domain;

import fr.arrestier.todomvc.domain.exception.AlreadyExisting;
import fr.arrestier.todomvc.domain.exception.InvalidData;
import fr.arrestier.todomvc.domain.exception.NotFound;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getAll() {
        return todoRepository.findAll();
    }

    public Todo create(String title) throws AlreadyExisting, InvalidData {
        if (title.isEmpty()) {
            throw new InvalidData("Title cannot be empty");
        }
        Todo todoToCreate = new Todo(title);

        try {
            return todoRepository.save(todoToCreate);
        } catch (RuntimeException e) {
            throw new AlreadyExisting(todoToCreate.getTitle());
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

    public Todo updateById(String id, String title, boolean completed, int order) throws AlreadyExisting, NotFound, InvalidData {
        if(todoRepository.findByTitle(title).isPresent()) {
            throw new AlreadyExisting(title);
        }

        var todo = todoRepository.findById(id);
        if (todo.isEmpty()) {
            throw new NotFound(id);
        }

        var foundTodo = todo.get();
        foundTodo.setTitle(title);
        foundTodo.setCompleted(completed);
        foundTodo.setOrder(order);
        return todoRepository.save(foundTodo);
    }

    public void deleteById(String id) throws NotFound {
        if(todoRepository.findById(id).isEmpty()) {
            throw new NotFound(id);
        }
        todoRepository.deleteById(id);
    }
}
