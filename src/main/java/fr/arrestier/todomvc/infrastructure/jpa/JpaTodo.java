package fr.arrestier.todomvc.infrastructure.jpa;

import fr.arrestier.todomvc.domain.Todo;
import jakarta.persistence.*;

@Entity
@Table(name = "todos")
class JpaTodo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true)
    public String title;

    public boolean completed;

    @Column(name = "priority")
    public int order;

    public String getId() {
        return id;
    }

    public JpaTodo() {}

    public JpaTodo(Todo todo) {
        this.id = todo.getId();
        this.title = todo.title;
        this.completed = todo.completed;
        this.order = todo.order;
    }

    public Todo asTodo() {
        return new Todo(id, title, completed, order);
    }
}
