import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Getter
public class Graph {

    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private final List<Integer> cities = Collections.synchronizedList(new ArrayList<>());
    private final List<Edge> edges = Collections.synchronizedList(new ArrayList<>());
}