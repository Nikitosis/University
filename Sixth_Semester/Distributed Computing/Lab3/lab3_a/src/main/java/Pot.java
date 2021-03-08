import java.util.concurrent.CountDownLatch;

public class Pot {
    private Integer size;
    private CountDownLatch countDownLatch;

    public Pot(Integer size) {
        this.size = size;
        countDownLatch = new CountDownLatch(size);
    }

    public void addHoney() {
        countDownLatch.countDown();
    }

    public void eatHoney() throws InterruptedException {
        countDownLatch.await();
        countDownLatch = new CountDownLatch(size);
    }
}
