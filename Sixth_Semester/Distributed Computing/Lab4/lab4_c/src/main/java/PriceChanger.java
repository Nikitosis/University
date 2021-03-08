import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.util.Random;

@AllArgsConstructor
public class PriceChanger implements Runnable {
    private final Graph graph;

    @SneakyThrows
    @Override
    public void run() {
        while(true) {
            Thread.sleep(1000);
            double priceMultiplier = new Random(System.currentTimeMillis()).nextDouble()*2 + 1;
            graph.getReadWriteLock().writeLock().lock();
            System.out.println("PriceChanger");
            for (Edge edge : graph.getEdges()) {
                edge.setPrice((int)(edge.getPrice() * priceMultiplier));
            }
            System.out.println("PriceChanger finished");
            graph.getReadWriteLock().writeLock().unlock();
        }
    }
}
