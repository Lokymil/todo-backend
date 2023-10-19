package fr.arrestier.todomvc.presentation;

import jakarta.validation.constraints.NotNull;

public class TodoCreationRequest {
    @NotNull
    public String title;
}
