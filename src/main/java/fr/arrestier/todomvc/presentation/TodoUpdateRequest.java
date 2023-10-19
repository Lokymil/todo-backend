package fr.arrestier.todomvc.presentation;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class TodoUpdateRequest {
    @NotBlank(message = "Title is mandatory")
    public String title;

    // TODO règle de présence obligatoire
    public boolean completed;

    @Min(value = 1)
    public int order;
}
