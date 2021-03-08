import java.util.concurrent.CyclicBarrier;

public class Smoker implements Runnable {
    private CyclicBarrier waitingTableBarrier;
    private CyclicBarrier waitingFinishSmokingBarrier;
    private Table table;
    private PartType searchedType;

    public Smoker(CyclicBarrier waitingTableBarrier, CyclicBarrier waitingFinishSmokingBarrier, Table table, PartType searchedType) {
        this.waitingTableBarrier = waitingTableBarrier;
        this.waitingFinishSmokingBarrier = waitingFinishSmokingBarrier;
        this.table = table;
        this.searchedType = searchedType;
    }

    @lombok.SneakyThrows
    @Override
    public void run() {
        while(true) {
            System.out.println("Smoker is coming to the table");
            waitingTableBarrier.await();
            if(table.getCurPartType().equals(searchedType)) {
                System.out.println("Creating a sigarette.");
                Thread.sleep(1000);
                System.out.println("Smoking a sigarette.");
                Thread.sleep(1000);
            }
            waitingFinishSmokingBarrier.await();
        }
    }
}
