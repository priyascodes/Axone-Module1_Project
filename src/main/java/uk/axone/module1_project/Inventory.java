package uk.axone.module1_project;


import java.util.*;

public class Inventory implements Comparable<Item> {

    public Map<Item, Integer> stock = new HashMap<Item, Integer>();

    public List<Item> listOfItems;

    public Inventory() throws InvalidDataException {
        this.load();
    }


    public void load () throws InvalidDataException {

         List<Item>listOfItems = new ArrayList<Item>();
// Manually instantiated Items - Hardcoded
        Item nokia = new Item("nokia", 878);
        nokia.setItemPrice(450);
        nokia.setItemDescription("Oldy but goody");
        listOfItems.add(nokia);

        Item iphone12 = new Item("iphone12", 100);
        iphone12.setItemDescription("Powerful and expensive");
        iphone12.setItemPrice(1000);
        listOfItems.add(iphone12);

        Item samsungGalaxy = new Item("samsungGalaxy", 102);
        samsungGalaxy.setItemDescription("Android versatile");
        samsungGalaxy.setItemPrice(600);
        listOfItems.add(samsungGalaxy);

        Item onePlus = new Item("onePlus", 103);
        onePlus.setItemDescription("Sturdy and robust");
        onePlus.setItemPrice(600);
        listOfItems.add(onePlus);


        stock.put(iphone12, 20);
        stock.put(nokia, 30);
        stock.put(samsungGalaxy, 40);
        stock.put(onePlus, 43);
        System.out.println("In load method");

//printout contents of stock
        for (Map.Entry<Item,Integer> entry: stock.entrySet()) {
            System.out.println(entry.getKey().getItemName());
            System.out.println(entry.getValue());
        }

//printout contents of listOfItems

        for (Item item : listOfItems) {
            System.out.println(item.getItemName());

        }
    }


//method to check for item

    public void checkItemFound(Item checkItem) throws ItemNotFoundException {

        if (stock.containsKey(checkItem)) {
            System.out.println("ITem found");
        }else{
            throw new ItemNotFoundException("Not found");
        }
    }


//method reduceStock()

    public boolean reduceStock(Item it, Integer quantity) throws ItemNotFoundException {

        System.out.println(it.getItemName());

        // check if item is found - exists
        try {
            checkItemFound(it);
        } catch (ItemNotFoundException inf) {
            System.out.println(inf.getMessage());
       }

        Integer oldQuantity = stock.get(it);
        Integer newQuantity;


        if (quantity < 0) {

            System.out.println("Invalid Quantity entered");
            return false;

        } else if (quantity <= oldQuantity && quantity >= 0) {
            newQuantity = oldQuantity - quantity;
            if (newQuantity == 0) {
                System.out.println("Item" + "  " + it.getItemName() + "  out of stock");
                stock.replace(it, oldQuantity, newQuantity);
                return true;
            } else {
                stock.replace(it, oldQuantity, newQuantity);
                return true;
            }

        } else if (quantity > oldQuantity) {

            System.out.println("Item quantity larger than in stock. Invalid.");
            return false;
        }
        return false;
    }
    @Override
    public int compareTo(Item o) {
        return 0;
    }


}





