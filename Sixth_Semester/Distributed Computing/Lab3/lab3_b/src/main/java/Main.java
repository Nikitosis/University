import java.util.concurrent.CyclicBarrier;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Hairdresser hairdresser = new Hairdresser();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

        new Thread(new HairdresserTask(hairdresser, cyclicBarrier)).start();

        Thread.sleep(1000);

        new Thread(new ClientTask(new Client(), hairdresser, cyclicBarrier)).start();

        Thread.sleep(2000);
        new Thread(new ClientTask(new Client(), hairdresser, cyclicBarrier)).start();
        new Thread(new ClientTask(new Client(), hairdresser, cyclicBarrier)).start();
        new Thread(new ClientTask(new Client(), hairdresser, cyclicBarrier)).start();
    }
}
