import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.BDDMockito.verify;

@ExtendWith(MockitoExtension.class)
public class SmartFridgeFeatureShould {

    @Mock
    private Printer printer;
    private Day day;

    @BeforeEach
    public void setUp(){
        day = new Day("18/10/2021");
    }

    @Test
    public void display_all_items_and_their_remaining_days_of_expiry(){

        SmartFridge smartFridge = new SmartFridge(printer, day);
        smartFridge.open();
        Item milk = new Item("Milk", "21/10/2021", ItemCondition.SEALED);
        Item cheese = new Item("Cheese", "18/11/2021", ItemCondition.SEALED);
        Item beef = new Item("Beef", "20/10/2021", ItemCondition.SEALED);
        Item lettuce = new Item("Lettuce", "22/10/2021", ItemCondition.SEALED);

        smartFridge.addItems(List.of(milk, cheese, beef, lettuce));
        smartFridge.close();

        day.end();

        smartFridge.open();
        smartFridge.close();

        smartFridge.open();
        smartFridge.close();

        smartFridge.open();
        smartFridge.removeItem("Milk");
        smartFridge.close();

        smartFridge.open();
        Item opened_milk = new Item("Milk", "20/10/2021", ItemCondition.OPENED);
        Item peppers = new Item("Peppers", "23/10/2021", ItemCondition.OPENED);
        smartFridge.addItems(List.of(opened_milk, peppers));
        smartFridge.close();

        day.end();

        smartFridge.open();
        smartFridge.removeItems(List.of("Beef", "Lettuce"));
        smartFridge.close();

        smartFridge.open();
        Item opened_lettuce = new Item("Lettuce", "22/10/2021", ItemCondition.OPENED);
        smartFridge.addItem(opened_lettuce);
        smartFridge.close();

        smartFridge.open();
        smartFridge.close();

        day.end();

        smartFridge.displayItems();

        verify(printer).execute("EXPIRED: Milk");
        verify(printer).execute("Lettuce: 0 day remaining");
        verify(printer).execute("Peppers: 1 day remaining");
        verify(printer).execute("Cheese: 27 days remaining");
    }
}
