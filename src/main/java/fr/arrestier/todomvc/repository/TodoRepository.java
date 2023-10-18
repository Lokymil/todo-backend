package fr.arrestier.todomvc.repository;

import fr.arrestier.todomvc.service.Todo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends CrudRepository<Todo, String> {
    List<Todo> findByTitle(String title);

    @Modifying
    @Query("delete Todo where completed = true")
    void deleteCompleted();
}