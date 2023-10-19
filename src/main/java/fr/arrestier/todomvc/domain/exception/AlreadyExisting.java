package fr.arrestier.todomvc.domain.exception;

public class AlreadyExisting extends Exception {
    public AlreadyExisting(String title) {
        super("Todo with title \"" + title + "\" already exists");
    }
}
