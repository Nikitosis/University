import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

public class Application {

    private static final List<List<Integer>> lists = new ArrayList<>();

	public static void main(String[] args) {

	    lists.add(new ArrayList<>(Arrays.asList(1,2,3,4)));
        lists.add(new ArrayList<>(Arrays.asList(1,2,3,4,1)));
        lists.add(new ArrayList<>(Arrays.asList(1,2,3,4,2)));

        AtomicBoolean isFinished = new AtomicBoolean(false);

        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new CheckerRunnable(lists, isFinished));

        List<Thread> threads = new ArrayList<>();

        for(int i=0;i<lists.size();i++) {
            threads.add(new Thread(new ListRunnable(cyclicBarrier, lists.get(i), isFinished)));
        }

        for (Thread thread : threads) {
            thread.start();
        }
    }

}
