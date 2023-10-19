package fr.arrestier.todomvc.presentation;

import jakarta.validation.constraints.NotNull;

public class TodoUpdateRequest {
    @NotNull
    public String title;

    @NotNull
    public Boolean completed;

    @NotNull
    public Integer order;
}
