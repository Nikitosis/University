import lombok.SneakyThrows;

public class BeeTask implements Runnable {
    private Pot pot;

    public BeeTask(Pot pot) {
        this.pot = pot;
    }

    @SneakyThrows
    public void run() {
        while(true) {
            Thread.sleep(1000);
            pot.addHoney();
            System.out.println("Bee added honey");
        }
    }
}
