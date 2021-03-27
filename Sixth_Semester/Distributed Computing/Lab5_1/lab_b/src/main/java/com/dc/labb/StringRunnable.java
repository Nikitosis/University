package com.dc.labb;

import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

public class StringRunnable implements Runnable {

    private final int stringIndex;
    private final CyclicBarrier cyclicBarrier;
    private List<StringBuilder> stringBuilders;
    private AtomicBoolean isFinished;
    
    public StringRunnable(int stringIndex, CyclicBarrier cyclicBarrier, List<StringBuilder> stringBuilders, AtomicBoolean isFinished) {
		super();
		this.stringIndex = stringIndex;
		this.cyclicBarrier = cyclicBarrier;
		this.stringBuilders = stringBuilders;
		this.isFinished = isFinished;
	}

	public void run() {
        Random random = new Random(System.currentTimeMillis());
        while (!isFinished.get()) {
            System.out.println(stringIndex);
            StringBuilder stringBuilder = stringBuilders.get(stringIndex);
            switch (random.nextInt(4)) {
                case 0:
                    replaceChars(stringBuilder, 'A', 'C');
                    break;
                case 1:
                    replaceChars(stringBuilder, 'C', 'A');
                    break;
                case 2:
                    replaceChars(stringBuilder, 'B', 'D');
                    break;
                case 3:
                    replaceChars(stringBuilder, 'D', 'B');
                    break;
                default:
                    break;
            }
            try {
                Thread.sleep(2000);
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    private void replaceChars(StringBuilder stringBuilder, char replaced, char replacer) {
        for (int i = 0; i < stringBuilder.length(); i++) {
            if (stringBuilder.charAt(i) == replaced) {
                stringBuilder.setCharAt(i, replacer);
            }
        }
    }
}
