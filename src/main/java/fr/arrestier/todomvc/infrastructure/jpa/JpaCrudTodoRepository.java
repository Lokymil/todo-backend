package fr.arrestier.todomvc.infrastructure.jpa;

import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
interface JpaCrudTodoRepository extends CrudRepository<JpaTodo, String> {
    List<JpaTodo> findAll();

    @Transactional
    long deleteByCompleted(boolean isCompleted);

    Optional<JpaTodo> findByTitle(String title);
}