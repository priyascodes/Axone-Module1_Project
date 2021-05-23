package uk.axone.module1_project;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {

    public Map<Item, Integer> cart;

    public Inventory shopInventory;

    // CONSTRUCTOR
    public ShoppingCart() throws InvalidDataException {

        cart = new HashMap<Item, Integer>();
        shopInventory = new Inventory();
    }  // End of Constructor


    //Method 1
   /* Adds a single item to the cart, checking if the Item is valid and in stock
    o Returns true if added successfully  */

    public boolean addToCart(Item it) throws ItemNotFoundException, ItemOutOfStockException, InvalidDataException {

        if (shopInventory.validateItem(it)) {
            int n = shopInventory.getItemStock(it);
            if (n > 0) {
                System.out.println("Item in stock. Quantity available is:  " + n);
                it = shopInventory.getItem(it);
                cart.put(it, n);
                for (Item it1 : cart.keySet()) {
                    System.out.println(it1.getItemName() + it1.getItemCode());

                }
                return true;
            } else {
                System.out.println("Sorry, Item is out of stock" + n);
                throw new ItemOutOfStockException("Item is Out of Stock");
            }
        } else {
            System.out.println("Item not Found");
            throw new ItemNotFoundException("Item not found");
        }


    }  // End of Method 1


    /* // Method 2
        o Adds multiple items to cart, checking if the Item is valid and in stock
        o Returns true if added successfully
    */
    public boolean addToCart(Item it, int quantity) throws ItemNotFoundException, ItemOutOfStockException {

        if (shopInventory.validateItem(it)) {
            int itemQuantityInStock = 0;
            it = shopInventory.getItem(it);

            itemQuantityInStock = shopInventory.getItemStock(it);
            if (itemQuantityInStock >= quantity) {
                System.out.println("Item in stock. Quantity available is:  " + itemQuantityInStock);
                cart.put(it, quantity);
                return true;

            } else if (itemQuantityInStock == 0) {
                throw new ItemOutOfStockException(" Item out of stock");
            } else {
                System.out.println("Do not have sufficient stock of this item");
                return false;
            }

        } else {
            throw new ItemOutOfStockException((" addToCart(Item, quantity) validate - Item Not FOund"));
        }
    }  // End of Method 2





/*
            if (shopInventory.validateItem(it)) {
                int n = shopInventory.getItemStock(it);
                if (n > quantity) {
                    System.out.println("Item in stock. Quantity available is:  " + n);
                    it = shopInventory.getItem(it);
                    if (cart.isEmpty()) {

                        cart.put(it, quantity);
                    }

                }

                for (Map.Entry<Item, Integer> it1 : cart.entrySet()) {
                    System.out.println(it1.getKey().getItemName() + "  " + it1.getValue());

                }
                return true;
            }
            /*else {
                System.out.println("Sorry, Item is out of stock"  +  n);
                throw new ItemOutOfStockException("Item is Out of Stock");
            }
        } else {
        System.out.println("Item not Found");
        throw new ItemNotFoundException("Item not found");
    } */


    /*  // Method 3
      o Removes a single item from the cart
      o Returns true if removed successfully
      */
    public boolean removeFromCart(Item it) throws ItemNotInCartException {
        return false;
    }

    /*   Method 4
    o Returns the total cost of items in the cart at any point
    */

//    public long calculateTotalCost(){

//    }


    /*  Method 5
    o Returns the items in the cart as an array
      */
    //       public Items[] getCartContents(){
    //
    //       }



/*   Method 6
o Will reduce the stock levels for the items being checkedOut  */

    public void checkout() {

    }

}
