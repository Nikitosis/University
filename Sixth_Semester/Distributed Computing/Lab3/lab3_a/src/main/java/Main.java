public class Main {
    public static void main(String[] args) throws InterruptedException {
        int N = 5;

        Pot pot = new Pot(11);

        for(int i=0;i<N;i++) {
            new Thread(new BeeTask(pot)).start();
        }

        new Thread(new BearTask(pot)).start();


        Thread.sleep(20000);
    }
}
