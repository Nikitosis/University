import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class CheckerRunnable implements Runnable {

    private List<List<Integer>> lists;
    private HashMap<Integer, Integer> count = new HashMap<>();
    private AtomicBoolean isFinished;

    public CheckerRunnable(List<List<Integer>> lists, AtomicBoolean isFinished) {
        this.lists = lists;
        this.isFinished = isFinished;
    }

    public void run() {
        count.clear();

        for (int i = 0; i < lists.size(); i++) {
            count.put(i, 0);
        }

        for (int i = 0; i < lists.size(); i++) {
            count(lists.get(i), i);
        }

        if (checkIfDone()) {
            isFinished.set(true);
        }
    }

    private void count(List<Integer> list, int index) {
        int sum = 0;
        for (int i = 0; i < list.size(); i++) {
            sum+=list.get(i);
        }
        count.put(index, sum);
    }

    private boolean checkIfDone() {
        System.out.println(count.get(0) + " " + count.get(1) + " " + count.get(2));
        if(count.get(0).equals(count.get(1)) && count.get(0).equals(count.get(2))) {
            System.out.println("Done, count success with numbers: " + count.get(0) + " " + count.get(1) + " " + count.get(2));
            return true;
        }
        return false;
    }
}
