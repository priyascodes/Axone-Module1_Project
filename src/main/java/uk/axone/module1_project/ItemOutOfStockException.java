package uk.axone.module1_project;

public class ItemOutOfStockException extends Exception {
    public ItemOutOfStockException(String message) {
        super(message);
    }
}
