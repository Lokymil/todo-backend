package fr.arrestier.todomvc.domain.exception;

public class InvalidData extends Exception {
    public InvalidData(String title) {
        super(title);
    }
}
