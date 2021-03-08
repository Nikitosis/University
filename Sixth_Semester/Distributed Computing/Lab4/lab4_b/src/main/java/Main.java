import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<List<Cell>> map = getMap(5, 5);

        new Thread(new Gardener(map)).start();
        new Thread(new Nature(map)).start();
        new Thread(new Monitor1(map)).start();
        new Thread(new Monitor2(map)).start();
    }

    private static List<List<Cell>> getMap(int n, int m) {
        List<List<Cell>> map = new ArrayList<>();

        for(int i=0;i<n;i++) {
            List<Cell> row = new ArrayList<>();
            for(int j=0;j<m;j++) {
                row.add(new Cell());
            }
            map.add(row);
        }

        return map;
    }
}
