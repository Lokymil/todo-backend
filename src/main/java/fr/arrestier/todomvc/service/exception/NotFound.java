package fr.arrestier.todomvc.service.exception;

public class NotFound extends RuntimeException {
    public NotFound(String id) {
        super("Todo with id " + id + " does not exist");
    }
}
