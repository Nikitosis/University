import java.util.List;
import java.util.concurrent.Semaphore;

public class CheckCellTask implements Runnable {
    private final List<List<Cell>> map;
    private final int id;

    public CheckCellTask(List<List<Cell>> map, int id) {
        this.map = map;
        this.id = id;
    }

    @lombok.SneakyThrows
    @Override
    public void run() {
        for(int i=0;i<map.size();i++) {
            for(int j=0;j<map.get(i).size();j++) {
                if (map.get(i).get(j).getInProgress().compareAndSet(false, true)) {

                    System.out.println("---------------");
                    System.out.println(String.format("Revising cell %d %d by bees id %d", i, j, id));
                    Thread.sleep(1000);
                    switch (map.get(i).get(j).getCellStatus()) {
                        case BEAR:
                            System.out.println(String.format("Cell %d %d revised. Bear found. Executing him", i, j));
                            map.get(i).get(j).setCellStatus(CellStatus.VISITED);
                            break;
                        case EMPTY:
                            System.out.println(String.format("Cell %d %d revised. Empty cell found. Going back", i, j));
                            map.get(i).get(j).setCellStatus(CellStatus.VISITED);
                            break;
                    }
                    map.get(i).get(j).setCellStatus(CellStatus.VISITED);
                    Thread.sleep(1000);
                }
            }
        }
    }
}
