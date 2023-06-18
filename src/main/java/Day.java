import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Day {

    private String date;
    private final int A_DAY = 1;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Day(String date) {
        this.date = date;
    }

    public void end() {
        LocalDate todaysDay = parseStringDateToLocalDate().plusDays(A_DAY);
        date = parseLocalDateToStringDate(todaysDay);
    }

    public String parseLocalDateToStringDate(LocalDate localDate) {
        return localDate.format(formatter);
    }

    public String todaysDate() {
        return date;
    }

    public LocalDate parseStringDateToLocalDate() {
        return LocalDate.parse(date, formatter);
    }

}
