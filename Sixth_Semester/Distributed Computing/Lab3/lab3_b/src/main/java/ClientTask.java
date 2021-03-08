import java.util.concurrent.CyclicBarrier;

public class ClientTask implements Runnable {
    private Client client;
    private Hairdresser hairdresser;
    private CyclicBarrier cyclicBarrier;

    public ClientTask(Client client, Hairdresser hairdresser, CyclicBarrier cyclicBarrier) {
        this.client = client;
        this.hairdresser = hairdresser;
        this.cyclicBarrier = cyclicBarrier;
    }

    @lombok.SneakyThrows
    @Override
    public void run() {
        hairdresser.getClientQueue().add(client);
        if(hairdresser.getClientQueue().size() > 1) {
            System.out.println("Queue is not empty. Sleeping");
            client.sleep();
        }
        hairdresser.wakeUp();

        cyclicBarrier.await();
        client.sleep();
    }
}
