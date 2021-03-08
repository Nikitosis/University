import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@RequiredArgsConstructor
public class CityChanger implements Runnable{

    private final Graph graph;

    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            Thread.sleep(1000);
            graph.getReadWriteLock().writeLock().lock();
            System.out.println("CityChanger");
            int rand = new Random(System.currentTimeMillis()).nextInt(2);
            if (rand == 0) {
                System.out.println("Adding city");
                Set<Integer> cities = new HashSet<>();
                for(Integer city : graph.getCities()) {
                    cities.add(city);
                }
                int cityNum = 0;
                while(cities.contains(cityNum)) {
                    cityNum++;
                }

                graph.getCities().add(cityNum);
            } else {
                System.out.println("Removing city");
                if(graph.getCities().size() > 1) {
                    int city = new Random(System.currentTimeMillis()).nextInt(graph.getCities().size());
                    List<Edge> foundEdges = new ArrayList<>();
                    for (Edge edge : graph.getEdges()) {
                        if (edge.getA() == city || edge.getB() == city) {
                            foundEdges.add(edge);
                        }
                    }
                    graph.getEdges().removeAll(foundEdges);
                    graph.getCities().remove(city);
                }
            }
            System.out.println("CityChanger finished");
            graph.getReadWriteLock().writeLock().unlock();
        }
    }
}
