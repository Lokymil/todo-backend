package fr.arrestier.todomvc.infrastructure.jpa;

import fr.arrestier.todomvc.domain.Todo;
import fr.arrestier.todomvc.domain.TodoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JpaTodoRepository implements TodoRepository {
    private final JpaCrudTodoRepository jpaTodoRepository;

    public JpaTodoRepository(JpaCrudTodoRepository jpaTodoRepository) {
        this.jpaTodoRepository = jpaTodoRepository;
    }

    @Override
    public List<Todo> findAll() {
        return jpaTodoRepository.findAll().stream().map(JpaTodo::asTodo).collect(Collectors.toList());
    }

    @Override
    public Optional<Todo> findById(String id) {
        return jpaTodoRepository.findById(id).map(JpaTodo::asTodo);
    }

    @Override
    public Optional<Todo> findByTitle(String title) {
        return jpaTodoRepository.findById(title).map(JpaTodo::asTodo);
    }

    @Override
    public Todo save(Todo todo) {
        return jpaTodoRepository.save(new JpaTodo(todo)).asTodo();
    }

    @Override
    public void deleteAll() {
        jpaTodoRepository.deleteAll();
    }

    @Override
    public void deleteById(String id) {
        jpaTodoRepository.deleteById(id);
    }

    @Override
    public long deleteByCompleted(boolean completed) {
        return jpaTodoRepository.deleteByCompleted(completed);
    }
}
