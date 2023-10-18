package fr.arrestier.todomvc.repository;

import fr.arrestier.todomvc.service.Todo;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TodoRepository extends CrudRepository<Todo, String> {
    @Transactional
    long deleteByCompleted(boolean isCompleted);
}