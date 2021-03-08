import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Hairdresser {
    private AtomicBoolean isSleeping = new AtomicBoolean(false);
    private List<Client> clientQueue = Collections.synchronizedList(new ArrayList<>());

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

    public List<Client> getClientQueue() {
        return clientQueue;
    }

}
