import java.util.HashMap;
import java.util.Map;

public class EventTracker implements Tracker {

    private static EventTracker INSTANCE = new EventTracker();

    private Map<String, Integer> tracker;

    private EventTracker() {
        this.tracker = new HashMap<>();
    }

    synchronized public static EventTracker getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EventTracker();
        }
        return INSTANCE;
    }

    synchronized public void push(String message) {
        Integer x = tracker.getOrDefault(message, 0);
        tracker.put(message, x+1);
    }

    @Override
    synchronized public Boolean has(String message) {
        Integer x = tracker.getOrDefault(message, 0);
        return tracker.containsKey(message) && x > 0;
    }

    @Override
    synchronized public void handle(String message, EventHandler e) {
        try {


            e.handle();
            Integer x = tracker.getOrDefault(message, 0);
            tracker.put(message, x - 1);
        } catch (NullPointerException n){
            System.out.println("Message not found");
        }
    }

    public Map<String, Integer> getTracker() {
        return tracker;
    }


    // Do not use this. This constructor is for tests only
    // Using it breaks the singleton class
    EventTracker(Map<String, Integer> tracker) {
         this.tracker = tracker;
    }
}
