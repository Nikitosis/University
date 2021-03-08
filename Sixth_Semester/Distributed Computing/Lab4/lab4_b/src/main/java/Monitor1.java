import java.util.List;

public class Monitor1 implements Runnable {
    private List<List<Cell>> map;

    public Monitor1(List<List<Cell>> map) {
        this.map = map;
    }

    @lombok.SneakyThrows
    @Override
    public void run() {
        while(true) {
            Thread.sleep(5000);
            System.out.println();
            System.out.println();
            for(int i=0;i<map.size();i++) {
                System.out.println();
                for(int j=0;j<map.get(i).size();j++) {
                    Cell cell = map.get(i).get(j);
                    CellStatus cellStatus = cell.getCellStatus();
                    if(cellStatus.equals(CellStatus.OK)) {
                        System.out.print("1 ");
                    } else
                    {
                        System.out.print("0 ");
                    }
                }
            }
        }
    }
}
