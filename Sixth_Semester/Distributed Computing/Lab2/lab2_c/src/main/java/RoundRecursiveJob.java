import lombok.SneakyThrows;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RoundRecursiveJob implements Callable<Monk> {
    private final List<Monk> monks;

    public RoundRecursiveJob(List<Monk> monks) {
        this.monks = monks;
    }

    @SneakyThrows
    @Override
    public Monk call() {
        if(monks.size() > 2) {
            System.out.println("Monks amount > 2. Separating round");
            List<Monk> firstPartMonks = monks.subList(0, monks.size()/2);
            List<Monk> secondPartMonks = monks.subList(monks.size()/2 + 1, monks.size());
            ExecutorService executor = Executors.newFixedThreadPool(2);
            Future<Monk> firstPartWinnerFuture = executor.submit(new RoundRecursiveJob(firstPartMonks));
            Future<Monk> secondPartWinnerFuture = executor.submit(new RoundRecursiveJob(secondPartMonks));

            Monk firstPartWinner = firstPartWinnerFuture.get();
            Monk secondPartWinner = secondPartWinnerFuture.get();

            executor.shutdown();

            return fight(firstPartWinner, secondPartWinner);
        }
        if(monks.size() == 2) {
            System.out.println("Monks amount == 2. Fighting");
            return fight(monks.get(0), monks.get(1));
        }

        System.out.println("Monks amount == 1. Returning the monk");
        return monks.get(0);
    }

    private Monk fight(Monk monk1, Monk monk2) throws InterruptedException {
        Random random = new Random(System.currentTimeMillis());

        System.out.println(String.format("Fighting monk1 energy=%d. monk2 enegry = %d", monk1.getEnergy(), monk2.getEnergy()));
        Thread.sleep(random.nextInt(1000));
        if(monk1.getEnergy() > monk2.getEnergy()) {
            System.out.println("Monk1 won");
            return monk1;
        } else if (monk1.getEnergy() < monk2.getEnergy()) {
            System.out.println("Monk2 won");
            return monk2;
        } else {
            if(random.nextInt(2) == 0) {
                System.out.println("Monk1 won");
                return monk1;
            } else {
                System.out.println("Monk2 won");
                return monk2;
            }
        }
    }
}
