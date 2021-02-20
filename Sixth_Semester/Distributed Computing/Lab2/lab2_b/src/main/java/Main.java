import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        List<Thing> things = Collections.synchronizedList(new ArrayList<>());
        things.add(new Thing("TV", 10));
        things.add(new Thing("Knife", 20));
        things.add(new Thing("Computer", 5));
        things.add(new Thing("Spoon", 1));
        things.add(new Thing("Fork", 2));

        List<Thing> stolenThings = Collections.synchronizedList(new ArrayList<>());
        List<Thing> deliveredThings = Collections.synchronizedList(new ArrayList<>());

        AtomicBoolean isStealingFinished = new AtomicBoolean(false);
        AtomicBoolean isDeliveringFinished = new AtomicBoolean(false);

        new Thread(new StealJob(things, stolenThings, isStealingFinished)).start();
        new Thread(new DeliveringJob(stolenThings, deliveredThings, isStealingFinished, isDeliveringFinished)).start();
        new Thread(new CalculateJob(deliveredThings, isDeliveringFinished)).start();
    }
}
