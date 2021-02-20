import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    private static int N = 5;
    private static int M = 5;
    private static int BEES = 5;

    public static void main(String[] args) {
        List<List<Cell>> map = buildMap(N, M);
        drawMap(map);

        for(int i=0;i<BEES;i++) {
            new Thread(new CheckCellTask(map, i)).start();
        }
    }

    private static void drawMap(List<List<Cell>> map) {
        for(int i=0;i<map.size();i++) {
            for(int j=0;j<map.get(i).size();j++) {
                switch (map.get(i).get(j).getCellStatus()) {
                    case BEAR:
                        System.out.print("B");
                        break;
                    case EMPTY:
                        System.out.print("-");
                        break;
                    case VISITED:
                        System.out.print("X");
                        break;
                }
                System.out.print(" ");
            }
            System.out.println("");
        }
    }

    private static List<List<Cell>> buildMap(int n, int m) {
        List<List<Cell>> map = new ArrayList<List<Cell>>();

        for(int i=0;i<n;i++) {
            List<Cell> row = new ArrayList<Cell>();
            for(int j=0;j<m;j++) {
                row.add(new Cell(CellStatus.EMPTY, new AtomicBoolean(false)));
            }
            map.add(row);
        }

        int randomI = (int) (Math.random()*n);
        int randomJ = (int) (Math.random()*m);

        map.get(randomI).get(randomJ).setCellStatus(CellStatus.BEAR);

        return map;
    }
}
