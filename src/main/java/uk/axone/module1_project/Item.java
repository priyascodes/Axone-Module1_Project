package uk.axone.module1_project;



public class Item implements Comparable<Item> {

    private int itemCode;
    private String itemName;
    private String itemDescription;
    private double itemPrice;


    public Item(final Integer newItemCode) {
        setItemCode(newItemCode);

    }

    public Item(final String newItemName, final Integer newItemCode, final String newItemDescription,
                Double newItemPrice) {
        setItemCode(newItemCode);
        setItemDescription(newItemDescription);
        setItemName(newItemName);
        setItemPrice(newItemPrice);

    }

    public int getItemCode() {

        return itemCode;
    }

    public void setItemCode(final int itemCode) {

        this.itemCode = itemCode;
    }


    public String getItemName() {
        return itemName;
    }

    public void setItemName(final String itemName) {
        this.itemName = itemName;
    }


    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(final double itemPrice) {
        this.itemPrice = itemPrice;
    }

    @Override
    public int compareTo(Item o) {
        return 0;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(final String itemDescription) {
        this.itemDescription = itemDescription;
    }
}
