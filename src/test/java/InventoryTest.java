import org.junit.jupiter.api.Test;
import uk.axone.module1_project.InvalidDataException;
import uk.axone.module1_project.Inventory;
import uk.axone.module1_project.Item;
import uk.axone.module1_project.ItemNotFoundException;

public class InventoryTest {

    @Test
    public void InventoryTest1() throws InvalidDataException {
        Inventory myShop = new Inventory();

    }

    @Test
    public void InventoryTest2() throws InvalidDataException {
        Inventory myShop2 = new Inventory();
        Item item2 = new Item(354);
        try {
            myShop2.validateItem(item2);
        } catch (ItemNotFoundException inf) {
            System.out.println(inf.getMessage());
        }
    }


    @Test

    public void InventoryTest3() throws ItemNotFoundException, InvalidDataException {
        Inventory myShop2 = new Inventory();
        Item item3 = new Item(100);
        int q;
        q = myShop2.getItemStock(item3);
        System.out.println("Quantity in stock in test3" + q);


    }


}
