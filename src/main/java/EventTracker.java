import java.util.HashMap;
import java.util.Map;

public class EventTracker implements Tracker {

    private static EventTracker INSTANCE = new EventTracker();

    private Map<String, Integer> tracker;

    private EventTracker() {
        this.tracker = new HashMap<>();
    }

    synchronized public static EventTracker getInstance() {
        return new EventTracker();
    }

    synchronized public void push(String message) {
        Integer x = tracker.getOrDefault(message, 0);
        tracker.put(message, x+1);
    }

    synchronized public Boolean has(String message) {
        Integer x = tracker.getOrDefault(message, 0);
        return tracker.containsKey(message) && x > 0;
    }

    synchronized public void handle(String message, EventHandler e) {
        Integer x = tracker.getOrDefault(message, 0);
        tracker.put(message, x - 1);
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
