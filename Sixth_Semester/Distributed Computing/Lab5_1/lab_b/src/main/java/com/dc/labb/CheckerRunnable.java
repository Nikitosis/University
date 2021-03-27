package com.dc.labb;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class CheckerRunnable implements Runnable {

    private List<StringBuilder> stringBuilderList;
    private HashMap<Integer, Integer> aCount = new HashMap<>();
    private HashMap<Integer, Integer> bCount = new HashMap<>();
    private AtomicBoolean isFinished;

    public CheckerRunnable(List<StringBuilder> stringBuilderList, AtomicBoolean isFinished) {
        this.stringBuilderList = stringBuilderList;
        this.isFinished = isFinished;
    }

    public void run() {
        aCount.clear();
        bCount.clear();

        for (int i = 0; i < stringBuilderList.size(); i++) {
            aCount.put(i, 0);
            bCount.put(i, 0);
        }

        for (int i = 0; i < stringBuilderList.size(); i++) {
            countAB(stringBuilderList.get(i), i);
        }

        if (checkIfDone()) {
            isFinished.set(true);
        }
    }

    private void countAB(StringBuilder stringBuilder, int index) {
        for (int i = 0; i < stringBuilder.length(); i++) {
            if (stringBuilder.charAt(i) == 'A') {
                aCount.put(index, aCount.get(index) + 1);
            } else if (stringBuilder.charAt(i) == 'B') {
                bCount.put(index, bCount.get(index) + 1);
            }
        }
    }

    private boolean checkIfDone() {
        for(int i=0;i<stringBuilderList.size();i++) {
            for(int j=0;j<stringBuilderList.size();j++){
                for(int k=0;k<stringBuilderList.size();k++) {
                    if(i == j || j==k || k==i) {
                        continue;
                    }

                    if(aCount.get(i).equals(aCount.get(j)) && aCount.get(i).equals(aCount.get(k))) {
                        System.out.println("Done, count success with strings with numbers: " + i + " " + j + " " + k);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
