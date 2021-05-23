import org.junit.jupiter.api.Test;
import uk.axone.module1_project.*;

import java.util.Map;

public class ShoppingCartTest {

    @Test
    public void shoppingCartTest1() throws InvalidDataException, ItemOutOfStockException, ItemNotFoundException {
        ShoppingCart myCart = new ShoppingCart();
        Item it = new Item(565);
        myCart.addToCart(it);
        it.setItemCode(100);
        myCart.addToCart(it);
        it.setItemCode(878);
        myCart.addToCart(it);
        it.setItemCode(102);
        myCart.addToCart(it);
    }


    @Test
    public void shoppingCartTest2() throws InvalidDataException, ItemOutOfStockException, ItemNotFoundException {
        ShoppingCart myCart = new ShoppingCart();
        Item it = new Item(104);
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
        it.setItemCode(100);


    } // End of Test method
} // End of Class


