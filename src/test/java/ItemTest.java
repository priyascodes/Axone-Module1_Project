import org.junit.jupiter.api.Test;
import uk.axone.module1_project.*;


public class ItemTest {

    @Test

    public void ItemTest() {
        Item it = new Item(1000010);
        Item it1 = new Item("v               hbhj  ", 65765, "jbbjhbjhbjhbjbjhb", 123.3);

        System.out.println(it + it.getItemName());
        System.out.println(it1 + it1.getItemName());

    }
}
