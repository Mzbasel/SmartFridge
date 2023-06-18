import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Item {
    private final String name;
    private String expiry;
    private final ItemCondition condition;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH");

    public String name() {
        return name;
    }

    public String expiry() {
        return expiry;
    }

    public ItemCondition condition() {
        return condition;
    }

    public Item(String name, String expiry, ItemCondition condition) {
        this.name = name;
        this.expiry = expiry + " 00";
        this.condition = condition;
    }

    public void degradeExpiry(){
        LocalDateTime expiryTime = parseStringDateToLocalDateTime(expiry);

        if(condition.equals(ItemCondition.OPENED)){
            expiry = expiryTime.minusHours(5).format(dateTimeFormatter);
        }
        if(condition().equals(ItemCondition.SEALED)){
            expiry = expiryTime.minusHours(1).format(dateTimeFormatter);
        }
    }


    public String daysUntilExpiry(String todaysDate) {
        LocalDateTime expiryTime = parseStringDateToLocalDateTime(expiry);
        LocalDateTime todaysTime =  parseStringDateToLocalDateTime(formatDate(todaysDate));

        long daysBetween = ChronoUnit.DAYS.between(todaysTime, expiryTime);

        return expiryDays(daysBetween);

    }

    private String expiryDays(long daysBetween) {
        if(daysBetween > 1)
            return daysBetween + " days";
        if(daysBetween < 0)
            return "EXPIRED";
        return daysBetween + " day";
    }

    private LocalDateTime parseStringDateToLocalDateTime(String date){
        return LocalDateTime.parse(date, dateTimeFormatter);
    }

    private String formatDate(String date){
        return date + " 00";
    }
}
