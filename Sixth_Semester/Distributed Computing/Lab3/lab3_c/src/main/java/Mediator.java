import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Mediator implements Runnable {
    private CyclicBarrier waitingTableBarrier;
    private CyclicBarrier waitingFinishSmokingBarrier;
    private Table table;

    public Mediator(CyclicBarrier waitingTableBarrier, CyclicBarrier waitingFinishSmokingBarrier, Table table) {
        this.waitingTableBarrier = waitingTableBarrier;
        this.waitingFinishSmokingBarrier = waitingFinishSmokingBarrier;
        this.table = table;
    }

    @lombok.SneakyThrows
    @Override
    public void run() {
        while(true) {
            System.out.println("Mediator is putting part on the table");
            int num = (int)(Math.random()*3);
            if(num == 0) {
                table.setCurPartType(PartType.PAPER);
            } else if (num == 1) {
                table.setCurPartType(PartType.MATCH);
            } else if (num == 2) {
                table.setCurPartType(PartType.TOBACCO);
            }
            System.out.println("Table contains " + table.getCurPartType().toString());

            waitingTableBarrier.await();
            waitingTableBarrier.reset();
            waitingFinishSmokingBarrier.await();
            waitingFinishSmokingBarrier.reset();
        }
    }
}
