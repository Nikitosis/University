package com.tracking;

import java.time.Instant;

public class Tracker {
    public static void trackTime(Runnable runnable) {
        System.out.println("Started tracking time");
        Instant before = Instant.now();

        runnable.run();

        Instant after = Instant.now();

        long timeElapsed = after.toEpochMilli() - before.toEpochMilli();

        System.out.println("Finished tracking time. Execution time = " + timeElapsed + " milliseconds");
    }

    public static void trackMemory(Runnable runnable) {
        System.out.println("Started tracking memory");
        System.gc();
        long before = getCurrentUsedBytes();

        runnable.run();

        long after = getCurrentUsedBytes();

        long bytesUsed = after - before;

        System.out.println("Finished tracking memory. Used " + bytesUsed + " bytes");
    }

    private static long getCurrentUsedBytes() {
        Runtime rt = Runtime.getRuntime();
        return rt.totalMemory() - rt.freeMemory();
    }
}
