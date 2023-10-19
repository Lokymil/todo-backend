package fr.arrestier.todomvc.presentation;

public record TodoUpdateRequest(String title, boolean completed, int order) {
}
