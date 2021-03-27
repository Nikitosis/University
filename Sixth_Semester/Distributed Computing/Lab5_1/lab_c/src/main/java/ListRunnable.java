import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public class ListRunnable implements Runnable {

    private static final int MAX_SUM = 20;

    private final CyclicBarrier cyclicBarrier;
    private List<Integer> list;
    private AtomicBoolean isFinished;

    public ListRunnable(CyclicBarrier cyclicBarrier, List<Integer> list, AtomicBoolean isFinished) {
        this.cyclicBarrier = cyclicBarrier;
        this.list = list;
        this.isFinished = isFinished;
    }

    public void run() {
        while (!isFinished.get()) {
            int index = ThreadLocalRandom.current().nextInt(list.size());
            if (ThreadLocalRandom.current().nextInt() % 2 ==0) {
                list.set(index, list.get(index) + 1);
            } else {
                if(list.get(index) > 0) {
                    list.set(index, list.get(index) - 1);
                }
            }

            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
