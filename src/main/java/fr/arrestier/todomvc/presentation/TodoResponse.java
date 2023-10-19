package fr.arrestier.todomvc.presentation;

import fr.arrestier.todomvc.domain.Todo;

public record TodoResponse(String id, String title, boolean completed, int order, String url) {
    public TodoResponse(Todo todo, String serverUrl) {
        this(todo.getId(), todo.getTitle(), todo.isCompleted(), todo.getOrder(), serverUrl + "/todos/" + todo.getId());
    }
}
