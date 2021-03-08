import java.util.List;

public class Gardener implements Runnable {
    private List<List<Cell>> map;

    public Gardener(List<List<Cell>> map) {
        this.map = map;
    }

    @lombok.SneakyThrows
    @Override
    public void run() {
        while(true) {
            for(int i=0;i<map.size();i++) {
                for(int j=0;j<map.get(i).size();j++) {
                    System.out.println("Gardener checking cell " + i + " " + j);
                    Thread.sleep((long) (Math.random()*2000));
                    Cell cell = map.get(i).get(j);
                    if(cell.getCellStatus().equals(CellStatus.BAD)) {
                        System.out.println("Watering cell");
                        cell.setCellStatus(CellStatus.OK);
                    }
                }
            }
        }
    }
}
