package fr.arrestier.todomvc.controller;

import fr.arrestier.todomvc.service.Todo;

public record TodoResponse(String id, String title, boolean completed, int ordre, String url) {
    public static TodoResponse fromTodo(Todo todo) {
        // TODO make id cleaner for url
        return new TodoResponse(todo.getId(), todo.title, todo.completed, todo.order, "http://localhost:8080/todos/"+todo.getId());
    }
}
