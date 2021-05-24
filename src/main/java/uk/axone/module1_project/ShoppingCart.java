package uk.axone.module1_project;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {

    public Map<Item, Integer> cart;

    public Inventory shopInventory;

    // CONSTRUCTOR
    public ShoppingCart() {

        cart = new HashMap<Item, Integer>();
        try {
            shopInventory = new Inventory();
        } catch (InvalidDataException ide) {
            System.out.println(ide.getMessage());
        }

    }  // End of Constructor


    //Method 1
   /* Adds a single item to the cart, checking if the Item is valid and in stock
    o Returns true if added successfully  */

    public boolean addToCart(Item it) throws ItemNotFoundException, ItemOutOfStockException {


        if (shopInventory.validateItem(it)) {
            int n = shopInventory.getItemStock(it);
            if (n > 0) {
                System.out.println("Item in stock. Quantity available is:  " + n);
                it = shopInventory.getItem(it);
                cart.put(it, 1);
                return true;
            } else {
                System.out.println("Sorry, Item is out of stock" + n);
                throw new ItemOutOfStockException("Item is Out of Stock");
            }
        }
        return false;


    }  // End of Method 1  test


    /* // Second addToCart Method
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
        }
        return false;
    }  // End of Method 2


    /*  // Method 3
      o Removes a single item from the cart
      o Returns true if removed successfully
      */
    public boolean removeFromCart(Item it) throws ItemNotInCartException {
        it = shopInventory.getItem(it);
        if (cart.containsKey(it)) {
            cart.remove(it, it.getItemCode());
            return true;
        } else {
            throw new ItemNotInCartException("Item not in Cart exception at removeItemFromCart()");
        }
    }

    /*   Method 4
    o Returns the total cost of items in the cart at any point
    */

    public long calculateTotalCost() {
        long totalCost = 0;
        long productCost;
        for (Map.Entry<Item, Integer> itemInCart : cart.entrySet()) {
            Double doublePrice = itemInCart.getKey().getItemPrice();
            long price = doublePrice.longValue();

            int quantityRequired = itemInCart.getValue();
            long longQuantityRequired = quantityRequired;


            productCost = price * longQuantityRequired;
            totalCost += productCost;

        }
        return totalCost;
    }


    /*  Method 5
    o Returns the items in the cart as an array
      */
    public Item[] getCartContents() {
        int i = cart.size();
        Item[] listOfCartContents = new Item[i];
        int x = 0;
        for (Map.Entry<Item, Integer> cartContents : cart.entrySet()) {
            listOfCartContents[x] = cartContents.getKey();
            x++;
        }
        return listOfCartContents;
    }


/*   Method 6
o Will reduce the stock levels for the items being checkedOut  */

    public void checkout() throws InvalidDataException, ItemNotFoundException {
        for (Map.Entry<Item, Integer> cartContentsToCheckout : cart.entrySet()) {
            shopInventory.reduceStock(cartContentsToCheckout.getKey(), cartContentsToCheckout.getValue());
            cart.remove(cartContentsToCheckout.getKey(), cartContentsToCheckout.getValue());
        }

    }

}
