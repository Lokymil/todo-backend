package fr.arrestier.todomvc.domain;

import fr.arrestier.todomvc.domain.exception.InvalidData;

public class Todo {
    private String id;

    private String title;

    private boolean completed;

    private int order;

    public Todo(String title) {
        this.title = title;
        this.completed = false;
        // TODO make it dynamic
        this.order = 1;
    }

    public Todo(String id, String title, boolean completed, int order) {
        this.id = id;
        this.title = title;
        this.completed = completed;
        this.order = order;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) throws InvalidData {
        if (title.isEmpty()) {
            throw new InvalidData("Title cannot be empty");
        }
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) throws InvalidData {
        if (order <= 0) {
            throw new InvalidData("Order cannot be below 0");
        }
        this.order = order;
    }
}
