import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.Random;

@RequiredArgsConstructor
public class EdgeChanger implements Runnable {

    private final Graph graph;

    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            Thread.sleep(1000);
            graph.getReadWriteLock().writeLock().lock();
            System.out.println("EdgeChanger");
            if (graph.getCities().size() > 1) {
                int rand = new Random(System.currentTimeMillis()).nextInt(2);
                if (rand == 0) {
                    System.out.println("Adding edge");
                    int price = new Random(System.currentTimeMillis()).nextInt(1000);
                    int a = 0;
                    int b = 0;
                    while (a == b) {
                        a = new Random(System.currentTimeMillis()).nextInt(graph.getCities().size());
                        b = new Random(System.currentTimeMillis()).nextInt(graph.getCities().size());
                    }
                    graph.getEdges().add(new Edge(a, b, price));
                } else if(graph.getEdges().size() > 0) {
                    System.out.println("Removing edge");
                    int edge = new Random(System.currentTimeMillis()).nextInt(graph.getEdges().size());
                    graph.getEdges().remove(edge);
                }
            }
            System.out.println("PriceChanger finished");
            graph.getReadWriteLock().writeLock().unlock();
        }
    }
}
