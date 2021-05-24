import org.junit.jupiter.api.Test;
import uk.axone.module1_project.*;

import java.util.Map;

public class ShoppingCartTest {

    @Test
    public void shoppingCartTest1() {
        ShoppingCart myCart = new ShoppingCart();

   /*     Item it = new Item(565);
        myCart.addToCart(it);
        it.setItemCode(100);
        myCart.addToCart(it);
        it.setItemCode(878);
        myCart.addToCart(it);
        it.setItemCode(102);
        myCart.addToCart(it); */
    }


    @Test
    public void shoppingCartTest2() throws ItemOutOfStockException {
        ShoppingCart myCart2 = new ShoppingCart();

        Item it2 = new Item(100);
        try {
            myCart2.addToCart(it2);
        } catch (ItemNotFoundException inf) {
            System.out.println(inf.getMessage());
        }
        for (Map.Entry<Item, Integer> cartContent : myCart2.cart.entrySet()) {

            System.out.println(cartContent.getKey().getItemName() + "  " + cartContent.getValue());
        }
    }


    @Test
    public void shoppingCartTest3() {
        ShoppingCart myCart3 = new ShoppingCart();
        Item it = new Item(104);
        try {

            myCart3.addToCart(it, 15);

            System.out.println("this CART CONTENTS");

            for (Map.Entry<Item, Integer> thisCart : myCart3.cart.entrySet()) {
                System.out.println(thisCart.getKey().getItemName() + "   " + thisCart.getValue());
            }
        } catch (ItemNotFoundException inf) {
            System.out.println("That didn't work");
        } catch (ItemOutOfStockException iofs) {
            System.out.println("Item out of stock");
        }
        it.setItemCode(100);


    } // End of Test method

    @Test
    public void shoppingCartTest4() throws ItemOutOfStockException, ItemNotFoundException {
        ShoppingCart myCart4 = new ShoppingCart();
        Item it4 = new Item(102);
        try {

            myCart4.addToCart(it4, 1);

            System.out.println("this CART CONTENTS");

            for (Map.Entry<Item, Integer> thisCart : myCart4.cart.entrySet()) {
                System.out.println(thisCart.getKey().getItemName() + "   " + thisCart.getValue());
            }
        } catch (ItemNotFoundException inf) {
            System.out.println("That didn't work");
        } catch (ItemOutOfStockException iofs) {
            System.out.println("Item out of stock");
        }
        it4.setItemCode(100);
        myCart4.addToCart(it4, 1);
        System.out.println(myCart4.calculateTotalCost());


    } // End of Test method


    @Test
    public void completeJourneyTest() throws InvalidDataException, ItemNotFoundException {
        ShoppingCart myCart = new ShoppingCart();
        Item it = new Item(100);
        try {

            myCart.addToCart(it, 15);

            System.out.println("this CART CONTENTS");

            for (Map.Entry<Item, Integer> thisCart : myCart.cart.entrySet()) {
                System.out.println(thisCart.getKey().getItemName() + "   " + thisCart.getValue());
            }
        } catch (ItemNotFoundException inf) {
            System.out.println("That didn't work");
        } catch (ItemOutOfStockException iofs) {
            System.out.println("Item out of stock");
        }
        System.out.println(myCart.calculateTotalCost());

        myCart.checkout();
        if (myCart.getCartContents().length == 0) {
            System.out.println("Cart is Empty");
        }


        for (Map.Entry<Item, Integer> thisCart : myCart.cart.entrySet()) {
            System.out.println(" Cart contents after checkout" + thisCart.getKey().getItemName() + "   " + thisCart.getValue());
        }

    }

}    // End of Class

