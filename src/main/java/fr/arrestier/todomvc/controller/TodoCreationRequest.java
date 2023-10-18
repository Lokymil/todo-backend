package fr.arrestier.todomvc.controller;

import jakarta.validation.constraints.NotBlank;

public class TodoCreationRequest {
    @NotBlank(message = "Title is mandatory")
    public String title;
}
