package fr.arrestier.todomvc.domain.exception;

public class NotFound extends Exception {
    public NotFound(String id) {
        super("Todo with id " + id + " does not exist");
    }
}
