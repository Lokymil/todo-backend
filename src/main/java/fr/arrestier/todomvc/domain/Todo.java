package fr.arrestier.todomvc.domain;

public class Todo {
    private String id;

    public String title;

    public boolean completed;

    public int order;

    public String getId() {
        return id;
    }

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
}
