import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        List<Monk> monks = new ArrayList<>();
        monks.add(new Monk(50, Monastery.FIRST));
        monks.add(new Monk(10, Monastery.SECOND));
        monks.add(new Monk(77, Monastery.FIRST));
        monks.add(new Monk(33, Monastery.SECOND));
        monks.add(new Monk(88, Monastery.FIRST));
        monks.add(new Monk(10, Monastery.SECOND));
        monks.add(new Monk(100, Monastery.FIRST));
        monks.add(new Monk(50, Monastery.SECOND));
        monks.add(new Monk(33, Monastery.FIRST));

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<Monk> monkWinnerFuture = executor.submit(new RoundRecursiveJob(monks));
        Monk winner = monkWinnerFuture.get();
        System.out.println(String.format("Winner monk energy=%d monastery=%s", winner.getEnergy(), winner.getMonastery()));

        executor.shutdown();
    }
}
