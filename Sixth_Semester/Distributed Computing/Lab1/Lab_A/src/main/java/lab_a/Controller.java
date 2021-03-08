package lab_a;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Button thread1Priorityze;

    @FXML
    private Button thread2Priorityze;

    @FXML
    private Text positionText;

    @FXML
    private Text thread1Priority;

    @FXML
    private Text thread2Priority;

    @FXML
    private Button exitButton;

    private AtomicInteger position = new AtomicInteger(50);
    private AtomicBoolean isFirstThreadAlive = new AtomicBoolean(true);
    private AtomicBoolean isSecondThreadAlive = new AtomicBoolean(true);
    private final int thread1Value = 10;
    private final int thread2Value = 90;
    private final int THREAD_SPEED = 10;
    private Thread thread1;
    private Thread thread2;


    @FXML
    void initialize() {
        exitButton.setOnAction((e) -> {
           exit();
        });

        thread1 = initThread(thread1Value, isFirstThreadAlive);
        thread2 = initThread(thread2Value, isSecondThreadAlive);

        thread1.setPriority(Thread.MIN_PRIORITY);
        thread2.setPriority(Thread.MIN_PRIORITY);

        thread1.setName("Thread1");
        thread2.setName("Thread2");

        thread1Priorityze.setOnAction((e) -> changePriority(thread1, thread1Priority));
        thread2Priorityze.setOnAction((e) -> changePriority(thread2, thread2Priority));

        thread1.start();
        thread2.start();
    }

    private void changePriority(Thread thread, Text priorityText) {
        if(thread.getPriority() == Thread.MAX_PRIORITY) {
            thread.setPriority(Thread.MIN_PRIORITY);
            priorityText.setText(thread.getName() + ": LOW");
        } else {
            thread.setPriority(Thread.MAX_PRIORITY);
            priorityText.setText(thread.getName() + ": HIGH");
        }
    }

    private Thread initThread(int threadValue, AtomicBoolean isAlive) {
        return new Thread(() -> {
            while(isAlive.get()) {

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
        });
    }

    private void setBarPosition(int position) {
        synchronized (progressBar) {
            progressBar.setProgress(position / 100.0);
            positionText.setText(String.valueOf(progressBar.getProgress()));
        }
    }

    private void exit() {
        isFirstThreadAlive.set(false);
        isSecondThreadAlive.set(false);

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        Platform.exit();
    }
}
