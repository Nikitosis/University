package com.dc.labb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

public class Application {

	public static void main(String[] args) {

        StringBuilder sb1 = new StringBuilder("ABAAAAABBBBCD");
        StringBuilder sb2 = new StringBuilder("ABAAAAAAABCCDD");
        StringBuilder sb3 = new StringBuilder("AACAABCD");
        StringBuilder sb4 = new StringBuilder("ACCAACBCBCD");

        AtomicBoolean isFinished = new AtomicBoolean(false);

        List<StringBuilder> stringBuilders = Collections.synchronizedList(Arrays.asList(sb1,sb2,sb3,sb4));

        CyclicBarrier cyclicBarrier = new CyclicBarrier(4, new CheckerRunnable(stringBuilders, isFinished));

        List<Thread> threads = new ArrayList<>();

        for(int i=0;i<stringBuilders.size();i++) {
            threads.add(new Thread(new StringRunnable(i,cyclicBarrier, stringBuilders, isFinished)));
        }

        for (Thread thread : threads) {
            thread.start();
        }
    }

}
