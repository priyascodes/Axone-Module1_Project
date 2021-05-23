package uk.axone.module1_project;

public class ItemNotInCartException extends Exception {
    public ItemNotInCartException(String message) {
        super(message);
    }
}
