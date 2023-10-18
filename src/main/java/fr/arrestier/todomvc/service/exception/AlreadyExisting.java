package fr.arrestier.todomvc.service.exception;

public class AlreadyExisting extends RuntimeException {
    public AlreadyExisting(String title) {
        super("Todo with title \"" + title + "\" already exists");
    }
}
