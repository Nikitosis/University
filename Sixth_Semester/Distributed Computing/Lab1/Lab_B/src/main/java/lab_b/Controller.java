package lab_b;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Text positionText;

    @FXML
    private Text thread1Priority;

    @FXML
    private Text thread2Priority;

    @FXML
    private Button start1;

    @FXML
    private Button start2;

    @FXML
    private Button stop1;

    @FXML
    private Button stop2;

    @FXML
    private Button exitButton;

    @FXML
    private Text semaphoreState;

    private AtomicInteger position = new AtomicInteger(50);
    private AtomicBoolean isFirstThreadAlive = new AtomicBoolean(true);
    private AtomicBoolean isSecondThreadAlive = new AtomicBoolean(true);
    private final int thread1Value = 10;
    private final int thread2Value = 90;
    private final int THREAD_SPEED = 10;
    private Thread thread1;
    private Thread thread2;

    /**
     * 0 - none thread is active
     * 1 - first thread is active
     * 2 - second thread is active
     */
    private AtomicInteger semaphore = new AtomicInteger(0);

    @FXML
    void initialize() {
        isFirstThreadAlive.set(true);
        thread1 = initThread(thread1Value, isFirstThreadAlive, semaphore, 1);
        thread1.setPriority(Thread.MIN_PRIORITY);
        thread1Priority.setText(thread1.getName() + ": LOW");
        thread1.setName("Thread1");

        isSecondThreadAlive.set(true);
        thread2 = initThread(thread2Value, isSecondThreadAlive, semaphore, 2);
        thread2.setPriority(Thread.MAX_PRIORITY);
        thread2Priority.setText(thread2.getName() + ": HIGH");
        thread2.setName("Thread2");

        thread1.start();
        thread2.start();
    }

    private Thread initThread(int threadValue, AtomicBoolean isAlive, AtomicInteger semaphore, int semaphoreValue) {
        return new Thread(() -> {
            if(semaphore.compareAndSet(0, 1)) {
                while (!Thread.interrupted()) {

                    if (position.get() > threadValue) {
                        position.decrementAndGet();
                    } else if (position.get() < threadValue) {
                        position.addAndGet(1);
                    }
                    Platform.runLater(() -> setBarPosition(position.get()));

                    try {
                        Thread.sleep(THREAD_SPEED);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                semaphore.set(0);
            }
        });
    }

    private void setBarPosition(int position) {
        synchronized (progressBar) {
            progressBar.setProgress(position / 100.0);
            positionText.setText(String.valueOf(progressBar.getProgress() * 100));
        }
    }

    @FXML
    void exitApp(ActionEvent event) {
        thread1.interrupt();
        thread2.interrupt();

        Platform.exit();
    }

    @FXML
    void startFirst(ActionEvent event) {
        semaphore.set(1);

        start1.setDisable(true);
        start2.setDisable(true);
        stop1.setDisable(false);
        stop2.setDisable(true);

        thread2 = initThread(thread2Value, isSecondThreadAlive, semaphore, 2);
        thread2.setPriority(Thread.MAX_PRIORITY);
        thread2Priority.setText(thread2.getName() + ": HIGH");
        thread2.setName("Thread2");

        semaphoreState.setText("Locked by first");
    }

    @FXML
    void startSecond(ActionEvent event) {
        semaphore.set(2);

        start1.setDisable(true);
        start2.setDisable(true);
        stop1.setDisable(true);
        stop2.setDisable(false);

        semaphoreState.setText("Locked by second");
    }

    @FXML
    void stopFirst(ActionEvent event) throws InterruptedException {
        thread1.interrupt();

        start1.setDisable(false);
        start2.setDisable(false);
        stop1.setDisable(true);
        stop2.setDisable(true);

        semaphoreState.setText("Unlocked");
    }

    @FXML
    void stopSecond(ActionEvent event) throws InterruptedException {
        thread2.interrupt();

        start1.setDisable(false);
        start2.setDisable(false);
        stop1.setDisable(true);
        stop2.setDisable(true);

        semaphoreState.setText("Unlocked");
    }
}
