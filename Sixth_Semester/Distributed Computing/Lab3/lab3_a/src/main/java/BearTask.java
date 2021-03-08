import lombok.SneakyThrows;

public class BearTask implements Runnable {

    private Pot pot;

    public BearTask(Pot pot) {
        this.pot = pot;
    }

    @SneakyThrows
    public void run() {
        while(true) {
            pot.eatHoney();
            System.out.println("Bear ate all honey");
        }
    }
}
