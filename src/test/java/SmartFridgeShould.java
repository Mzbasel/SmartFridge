import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SmartFridgeShould {

    @Mock
    private Printer printer;
    private SmartFridge smartFridge;

    @BeforeEach
    public void setUp(){
        Day day = new Day("25/11/2021");
        smartFridge = new SmartFridge(printer, day);
    }

    @Test
    public void set_the_state_to_open(){
        smartFridge.open();
        assertThat(smartFridge.state()).isEqualTo(SmartFridge.State.OPEN);
    }

    @Test
    public void set_the_state_to_close(){
        smartFridge.open();
        smartFridge.close();
        assertThat(smartFridge.state()).isEqualTo(SmartFridge.State.CLOSE);
    }

    @Test
    public void add_items(){
        Item milk = new Item("Milk", "21/10/2021", ItemCondition.SEALED);
        Item cheese = new Item("Cheese", "18/11/2021", ItemCondition.SEALED);

        smartFridge.open();
        smartFridge.addItems(List.of(milk, cheese));

        assertThat(smartFridge.items()).containsExactlyInAnyOrder(milk, cheese);
    }

    @Test
    public void not_add_items_when_it_is_closed(){
        Item milk = new Item("Milk", "21/10/2021", ItemCondition.SEALED);
        Item cheese = new Item("Cheese", "18/11/2021", ItemCondition.SEALED);

        smartFridge.close();

        assertThatExceptionOfType(SmartFridgeIsClosed.class)
                .isThrownBy(() -> smartFridge.addItems(List.of(milk, cheese)));
    }

    @Test
    public void add_an_item(){
        Item milk = new Item("Milk", "21/10/2021", ItemCondition.SEALED);

        smartFridge.open();
        smartFridge.addItem(milk);

        assertThat(smartFridge.items()).containsExactlyInAnyOrder(milk);
    }

    @Test
    public void remove_an_item(){
        Item milk = new Item("Milk", "21/10/2021", ItemCondition.SEALED);
        Item cheese = new Item("Cheese", "18/11/2021", ItemCondition.SEALED);

        smartFridge.open();
        smartFridge.addItems(List.of(milk, cheese));
        smartFridge.removeItem("Milk");

        assertThat(smartFridge.items()).containsExactlyInAnyOrder(cheese);
    }

}