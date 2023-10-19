package fr.arrestier.todomvc.infrastructure;

import fr.arrestier.todomvc.domain.Todo;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TodoRepository extends CrudRepository<Todo, String> {
    @Transactional
    long deleteByCompleted(boolean isCompleted);

    Optional<Todo> findByTitle(String title);
}