import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@RequiredArgsConstructor
public class PriceChecker implements Runnable {
    private final Graph graph;

    @SneakyThrows
    @Override
    public void run() {
        while(true) {
            Thread.sleep(500);
            graph.getReadWriteLock().readLock().lock();
            System.out.println("PriceChecker");
            if(graph.getCities().size() > 1) {
                int a = 0;
                int b = 0;
                while (a == b) {
                    a = new Random(System.currentTimeMillis()).nextInt(graph.getCities().size());
                    b = new Random(System.currentTimeMillis()).nextInt(graph.getCities().size());
                }

                Set<Integer> visited = new HashSet<>();
                int rootPrice = dfs(a, visited);

                if (rootPrice != -1) {
                    System.out.println("Root between " + a + " and " + b + " found. Price=" + rootPrice);
                } else {
                    System.out.println("Root between " + a + " and " + b + " not found");
                }
            }

            System.out.println("PriceChecker finished");
            graph.getReadWriteLock().readLock().unlock();
        }
    }

    private int dfs(int curCity, Set<Integer> visited) {
        if(visited.contains(curCity)) {
            return -1;
        }
        visited.add(curCity);

        for(Edge edge : graph.getEdges()) {
            if(edge.getA() == curCity) {
                int result = dfs(edge.getB(), visited);
                if(result != -1) {
                    return edge.getPrice() + result;
                }
            }
            if(edge.getB() == curCity) {
                int result = dfs(edge.getA(), visited);
                if(result != -1) {
                    return edge.getPrice() + result;
                }
            }
        }

        return -1;
    }
}
