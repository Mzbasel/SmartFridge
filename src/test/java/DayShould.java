import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class DayShould {

    @Test
    public void pass(){
        Day day = new Day("22/12/2023");
        day.end();
        assertThat(day.todaysDate()).isEqualTo("23/12/2023");
    }

    @Test
    public void pass_to_next_month_when_we_are_at_the_end_of_a_month() {
        Day day = new Day("30/04/2023");
        day.end();
        assertThat(day.todaysDate()).isEqualTo("01/05/2023");
    }

}