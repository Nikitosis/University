import java.util.concurrent.CyclicBarrier;

public class HairdresserTask implements Runnable {
    private Hairdresser hairdresser;
    private CyclicBarrier cyclicBarrier;

    public HairdresserTask(Hairdresser hairdresser, CyclicBarrier cyclicBarrier) {
        this.hairdresser = hairdresser;
        this.cyclicBarrier = cyclicBarrier;
    }

    @lombok.SneakyThrows
    @Override
    public void run() {
        while(true) {
            while(hairdresser.getClientQueue().isEmpty()) {
                System.out.println("Queue is empty. Sleeping");
                hairdresser.sleep();
                System.out.println("Woke up by client. Starting cutting");
            }

            Client client = hairdresser.getClientQueue().get(0);
            client.wakeUp();

            System.out.println("Starting cutting client");
            //starting cutting
            cyclicBarrier.await();

            Thread.sleep(1000);

            System.out.println("Finished cutting. Wake up client");
            client.wakeUp();

            System.out.println("Following client to exit");

            hairdresser.getClientQueue().remove(client);
        }
    }
}
