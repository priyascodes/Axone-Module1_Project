package uk.axone.module1_project;



public class Item implements Comparable<Item>  {

    private int itemCode;
    private String itemName;
    private String itemDescription;
    private int itemPrice;

    public Item(String newItemName, Integer newItemCode) throws InvalidDataException{
        try {
            setItemCode(newItemCode);
        } catch (InvalidDataException de){
           System.out.println("Invalid Code");;
            System.out.println(de.getMessage());
        }
        setItemName(newItemName);

    }

    public int getItemCode() {

        return itemCode;
    }

    public void setItemCode(int itemCode) throws InvalidDataException {

        if (itemCode > 99 && itemCode < 1000) {

            this.itemCode = itemCode;

        } else {

            throw new InvalidDataException("Invalid Code1");
        }
    }


    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }



    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    @Override
    public int compareTo(Item o) {
        return 0;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
}
