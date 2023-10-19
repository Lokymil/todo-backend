package fr.arrestier.todomvc.domain;

import java.util.List;
import java.util.Optional;

public interface TodoRepository {
    List<Todo> findAll();

    Optional<Todo> findById(String id);

    Optional<Todo> findByTitle(String title);

    Todo save(Todo todo);

    void deleteAll();

    void deleteById(String id);

    long deleteByCompleted(boolean completed);
}
