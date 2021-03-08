import java.util.concurrent.atomic.AtomicBoolean;

public class Client {
    private AtomicBoolean isSleeping = new AtomicBoolean(false);

    public synchronized void sleep() {
        isSleeping.set(true);
        while(isSleeping.get()) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void wakeUp() {
        isSleeping.set(false);
        notifyAll();
    }
}
