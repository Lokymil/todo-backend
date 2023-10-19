package fr.arrestier.todomvc.presentation;

import jakarta.validation.constraints.NotBlank;

public class TodoCreationRequest {
    @NotBlank(message = "Title is mandatory")
    public String title;
}
