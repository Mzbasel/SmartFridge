import java.util.ArrayList;
import java.util.List;

public class SmartFridge {

    private State state;
    private final List<Item> items;
    private final Printer printer;
    private final Day day;

    public SmartFridge(Printer printer, Day day) {
        this.printer = printer;
        this.day = day;
        this.state = State.CLOSE;
        items = new ArrayList<>();
    }

    public void open() {
        this.state = State.OPEN;
        degradeExpiryOfAllItems();
    }

    private void degradeExpiryOfAllItems() {
        for (Item item: items) {
            item.degradeExpiry();
        }
    }

    public void close() {
        this.state = State.CLOSE;
    }

    public void addItem(Item item) {
        checkIfClosed();
        items.add(item);
    }

    public void addItems(List<Item> items) {
        checkIfClosed();
        this.items.addAll(items);
    }

    public void removeItem(String name) {
        checkIfClosed();
        items.removeIf(item -> item.name().equals(name));
    }

    public void removeItems(List<String> names) {
        checkIfClosed();
        for (String name: names) {
            this.items.removeIf(item -> item.name().equals(name));
        }
    }

    public State state() {
        return state;
    }

    public List<Item> items() {
        return items;
    }

    public void displayItems() {
        for (Item item: items) {
            if(item.daysUntilExpiry(day.todaysDate()).equals("EXPIRED")){
                printer.execute(item.daysUntilExpiry(day.todaysDate()) + ": " + item.name());
            }else {
                printer.execute(item.name() + ": " + item.daysUntilExpiry(day.todaysDate()) + " remaining");
            }
        }
    }

    protected enum State {
        OPEN, CLOSE
    }

    private void checkIfClosed() {
        if(state.equals(State.CLOSE))
            throw new SmartFridgeIsClosed();
    }
}
