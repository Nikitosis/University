import lombok.SneakyThrows;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class CalculateJob implements Runnable {
    private final List<Thing> deliveredThings;
    private final AtomicBoolean isDeliveringFinished;
    private int totalPrice = 0;


    public CalculateJob(List<Thing> deliveredThings, AtomicBoolean isDeliveringFinished) {
        this.deliveredThings = deliveredThings;
        this.isDeliveringFinished = isDeliveringFinished;
    }

    @SneakyThrows
    @Override
    public void run() {
        while(true) {
            if(!deliveredThings.isEmpty()) {
                Thing thing = deliveredThings.get(0);
                System.out.println(String.format("Calculating price of thing %s is %d", thing.getName(), thing.getPrice()));
                Thread.sleep(800);
                totalPrice+=thing.getPrice();
                System.out.println(String.format("Total price= %d", totalPrice));
                deliveredThings.remove(thing);
            } else {
                if(isDeliveringFinished.get()) {
                    System.out.println("Calculating finished Move, Move!");
                    return;
                }
            }
        }
    }
}
