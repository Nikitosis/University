import lombok.SneakyThrows;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class DeliveringJob implements Runnable {
    private final List<Thing> stolenThings;
    private final List<Thing> deliveredThings;
    private final AtomicBoolean isStealingFinished;
    private final AtomicBoolean isDeliveringFinished;

    public DeliveringJob(List<Thing> stolenThings, List<Thing> deliveredThings, AtomicBoolean isStealingFinished, AtomicBoolean isDeliveringFinished) {
        this.stolenThings = stolenThings;
        this.deliveredThings = deliveredThings;
        this.isStealingFinished = isStealingFinished;
        this.isDeliveringFinished = isDeliveringFinished;
    }

    @SneakyThrows
    @Override
    public void run() {
        while(true) {
            if(!stolenThings.isEmpty()) {
                Thing thing = stolenThings.get(0);
                System.out.println(String.format("Delivering thing %s", thing.getName()));
                Thread.sleep(1000);
                deliveredThings.add(thing);
                stolenThings.remove(thing);
            } else {
                if(isStealingFinished.get()) {
                    isDeliveringFinished.set(true);
                    System.out.println("Delivering finished Move, Move!");
                    return;
                }
            }
        }
    }
}
