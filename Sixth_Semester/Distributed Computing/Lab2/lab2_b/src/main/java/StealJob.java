import lombok.SneakyThrows;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class StealJob implements Runnable {
    private final List<Thing> things;
    private final List<Thing> stolenThings;
    private final AtomicBoolean isStealingFinished;

    public StealJob(List<Thing> things, List<Thing> stolenThings, AtomicBoolean isStealingFinished) {
        this.things = things;
        this.stolenThings = stolenThings;
        this.isStealingFinished = isStealingFinished;
    }

    @SneakyThrows
    @Override
    public void run() {
        for(Thing thing : things) {
            System.out.println(String.format("Stealing %s", thing.getName()));
            Thread.sleep(500);
            stolenThings.add(thing);
        }
        isStealingFinished.set(true);
        System.out.println("Stealing finished Move, Move!");
    }
}
