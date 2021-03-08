import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

public class Monitor2 implements Runnable{
    private List<List<Cell>> map;

    public Monitor2(List<List<Cell>> map) {
        this.map = map;
    }

    @lombok.SneakyThrows
    @Override
    public void run() {
        FileWriter fileWriter = new FileWriter("result.txt");
        PrintWriter printWriter = new PrintWriter(fileWriter);
        while(true) {
            Thread.sleep(5000);
            printWriter.println();
            printWriter.println();
            for(int i=0;i<map.size();i++) {
                printWriter.println();
                for(int j=0;j<map.get(i).size();j++) {
                    Cell cell = map.get(i).get(j);
                    CellStatus cellStatus = cell.getCellStatus();
                    if(cellStatus.equals(CellStatus.OK)) {
                        printWriter.print("1 ");
                    } else
                    {
                        printWriter.print("0 ");
                    }
                }
            }
            printWriter.flush();
        }
    }
}
