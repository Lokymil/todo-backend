package fr.arrestier.todomvc.presentation;

import fr.arrestier.todomvc.domain.Todo;

public record TodoResponse(String id, String title, boolean completed, int ordre, String url) {
    public static TodoResponse fromTodo(Todo todo) {
        // TODO make id cleaner for url
        return new TodoResponse(todo.getId(), todo.getTitle(), todo.isCompleted(), todo.getOrder(), "http://localhost:8080/todos/"+todo.getId());
    }
}
