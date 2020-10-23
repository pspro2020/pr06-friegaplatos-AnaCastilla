import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Dish {

    private static final int TRAY_CAPACITY = 10;

    private final DateTimeFormatter dateTimeFormatter =
            DateTimeFormatter.ofPattern("HH:mm:ss");
    private final ArrayList<Integer> cleanTray = new ArrayList<>();
    private final ArrayList<Integer> dryTray = new ArrayList<>();

    public void addToCleanTray(Integer dish) throws InterruptedException {
        synchronized (this) {
            while (cleanTray.size() >= TRAY_CAPACITY) {
                System.out.printf("%s - Dish Washer waiting for the dish clean tray to have room\n", LocalTime.now().format(dateTimeFormatter));
                wait();
            }
            cleanTray.add(dish);
            System.out.printf("%s - Dish Washer puts dish %d on the clean tray\n", LocalTime.now().format(dateTimeFormatter), dish);
            notifyAll();
        }
    }

    public Integer extractFromCleanTray() throws InterruptedException {
        Integer dish;
        synchronized (this) {
            while (cleanTray.isEmpty()) {
                System.out.printf("%s - Dish Dryer waiting for the clean tray to have a clean dish\n", LocalTime.now().format(dateTimeFormatter));
                wait();
            }
            dish = cleanTray.remove(0);
            System.out.printf("%s - Dish Dryer extracts clean dish %d from clean tray\n", LocalTime.now().format(dateTimeFormatter), dish);
            notifyAll();
            return dish;
        }
    }

    public void addToDryTray(Integer dish) throws InterruptedException {
        synchronized (this) {
            while (dryTray.size() >= TRAY_CAPACITY) {
                System.out.printf("%s - Dish Dryer waiting for the dish dry tray to have room\n", LocalTime.now().format(dateTimeFormatter));
                wait();
            }
            dryTray.add(dish);
            System.out.printf("%s - Dish Dryer puts dry dish %d on the dry tray\n", LocalTime.now().format(dateTimeFormatter), dish);
            notifyAll();
        }
    }

    public Integer extractFromDryTray() throws InterruptedException {
        Integer dish;
        synchronized (this) {
            while (dryTray.isEmpty()) {
                System.out.printf("%s - Organizer waiting for the dry tray to have a dry dish\n", LocalTime.now().format(dateTimeFormatter));
                wait();
            }
            dish = dryTray.remove(0);
            System.out.printf("%s - Organizer extracts clean dish %d from clean tray and put it in the cupboard\n", LocalTime.now().format(dateTimeFormatter), dish);
            notifyAll();
            return dish;
        }
    }
}
