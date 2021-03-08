import java.util.concurrent.CyclicBarrier;

public class Main {
    public static void main(String[] args) {
        CyclicBarrier waitingTableBarrier = new CyclicBarrier(4);
        CyclicBarrier waitingFinishSmokingBarrier = new CyclicBarrier(4);
        Table table = new Table();
        new Thread(new Mediator(waitingTableBarrier, waitingFinishSmokingBarrier, table)).start();
        new Thread(new Smoker(waitingTableBarrier, waitingFinishSmokingBarrier, table, PartType.MATCH)).start();
        new Thread(new Smoker(waitingTableBarrier, waitingFinishSmokingBarrier, table, PartType.PAPER)).start();
        new Thread(new Smoker(waitingTableBarrier, waitingFinishSmokingBarrier, table, PartType.TOBACCO)).start();
    }
}
