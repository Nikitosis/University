import java.util.List;

public class Nature implements Runnable {
    private List<List<Cell>> map;

    public Nature(List<List<Cell>> map) {
        this.map = map;
    }

    @lombok.SneakyThrows
    @Override
    public void run() {
        while(true) {
            for(int i=0;i<map.size();i++) {
                for(int j=0;j<map.get(i).size();j++) {
                    System.out.println("Nature checking cell " + i + " " + j);
                    Thread.sleep((long) (Math.random()*2000));

                    Cell cell = map.get(i).get(j);
                    int random = (int)(Math.random()*2);
                    if(random == 0) {
                        System.out.println("Nature makes cell bad");
                        cell.setCellStatus(CellStatus.BAD);
                    }
                }
            }
        }
    }
}
