import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;


class ItemShould {

    @Test
    public void calculate_the_remaining_days_of_unexpired_a_product(){
        Item item = new Item("Milk", "22/01/2023", ItemCondition.SEALED);
        assertThat(item.daysUntilExpiry("18/01/2023")).isEqualTo("4 days");
    }

    @Test
    public void calculate_the_remaining_days_of_an_expired_product(){
        Item item = new Item("Milk", "15/01/2023", ItemCondition.SEALED);
        assertThat(item.daysUntilExpiry("18/01/2023")).isEqualTo("EXPIRED");

    }

    @Test
    public void degrade_sealed_items_expiry_an_hour_when_the_fridge_has_opened(){
        Item item = new Item("Milk", "15/01/2023", ItemCondition.SEALED);
        item.degradeExpiry();
        assertThat(item.expiry()).isEqualTo("14/01/2023 23");
    }

    @Test
    public void degrade_opened_items_expiry_five_hours_when_the_fridge_has_opened(){
        Item item = new Item("Milk", "15/01/2023", ItemCondition.OPENED);
        item.degradeExpiry();
        assertThat(item.expiry()).isEqualTo("14/01/2023 19");
    }

    @Test
    public void calculate_the_remaining_days_of_an_item_that_has_less_than_a_day(){
        Item item = new Item("Milk", "17/01/2023", ItemCondition.OPENED);
        item.degradeExpiry();
        assertThat(item.daysUntilExpiry("16/01/2023")).isEqualTo("0 day");
    }

}