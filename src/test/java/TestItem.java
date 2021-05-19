import org.junit.jupiter.api.Test;
import uk.axone.module1_project.InvalidDataException;
import uk.axone.module1_project.Inventory;
import uk.axone.module1_project.Item;
import uk.axone.module1_project.ItemNotFoundException;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class TestItem {

   @Test
    public void testItem() throws InvalidDataException {

       Item motorola = new Item("motorola", 1234);
      /* List<Item> listOfItems = new ArrayList<>();

        Item nokia = new Item("nokia");
        nokia.setItemCode(9090);
        Item iphone12 = new Item(100);
       Item samsungGlaxy = new Item(101);
       Item onePlus = new Item(102);
       listOfItems.add(nokia);
       listOfItems.add(iphone12);
       listOfItems.add(samsungGlaxy);
       listOfItems.add(onePlus);

*/
   }

    @Test
    public void testInventory() throws InvalidDataException, ItemNotFoundException {
        Inventory newStock = new Inventory();
        Item motorola = new Item("motorola", 343);
        motorola.setItemPrice(30);
        newStock.stock.put(motorola,60);
        newStock.reduceStock(motorola,10);
       // System.out.println(newStock.stock.get(motorola));
       // boolean check = newStock.listOfItems.contains(motorola);
       // System.out.println(" this is the value of check;  " + check);


   }



}
