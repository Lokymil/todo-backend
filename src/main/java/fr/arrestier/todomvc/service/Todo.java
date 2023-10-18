package fr.arrestier.todomvc.service;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "todos")
public class Todo {
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

    public Todo() {}

    public Todo(String title) {
        this.title = title;
        this.completed = false;
        // TODO make it dynamic
        this.order = 1;
    }
}
