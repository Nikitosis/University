package com.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Task3 {
    @SneakyThrows
    public static void main(String[] args) {
        Car car1 = new Car("Car1", 1000, 5000);
        Car car2 = new Car("Car2", 2000, 5000);
        Car car3 = new Car("Car3", 1000, 5000);
        Car car4 = new Car("Car4", 2000, 5000);

        CarStop carStop = new CarStop(2);

        List<CarStop> carStops = Arrays.asList(carStop);

        new Thread(new CarAction(car1, carStops)).start();
        new Thread(new CarAction(car2, carStops)).start();
        new Thread(new CarAction(car3, carStops)).start();
        new Thread(new CarAction(car4, carStops)).start();
    }
}

@Data
@AllArgsConstructor
class Car{
    private String name;
    private Integer timeToWait;
    private Integer timeToStay;
}

@AllArgsConstructor
class CarAction implements Runnable {

    private Car car;
    private List<CarStop> carStops;

    @Override
    public void run() {
        //car is going to the carStop
        int curCarStop = 0;
        while(true) {
            if(curCarStop >= carStops.size()) {
                System.out.println(String.format("No more carStops for car=%s", car.getName()));
                return;
            }

            CarStop carStop = carStops.get(curCarStop);
            if(carStop.addCar(car)) {
                break;
            }
            System.out.println(String.format("Car = %s waited with no results. Moving to next carStop", car.getName()));
            curCarStop++;
        }

        try {
            Thread.sleep(car.getTimeToStay());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(String.format("Car = %s is leaving.", car.getName()));
        carStops.get(curCarStop).removeCar(car);
    }
}

class CarStop {
    private Map<Integer, Car> carPlace;
    private int size;
    private Semaphore semaphore;

    public CarStop(int size) {
        this.size = size;

        semaphore = new Semaphore(size);
        carPlace = new HashMap<Integer, Car>();
    }


    @SneakyThrows
    public boolean addCar(Car car) {
        if (!semaphore.tryAcquire(car.getTimeToWait(), TimeUnit.MILLISECONDS)) {
            return false;
        }

        synchronized (this) {
            for (int i = 0; i < size; i++) {
                if (!carPlace.containsKey(i)) {
                    System.out.println(String.format("Placing car=%s in spot=%d", car.getName(), i));
                    carPlace.put(i, car);
                    break;
                }
            }
        }

        return true;
    }

    public synchronized boolean removeCar(Car car) {
        for(int i=0;i<size;i++) {
            if(carPlace.containsKey(i) && carPlace.get(i).equals(car)) {
                carPlace.remove(i);
                return true;
            }
        }

        semaphore.release();

        return false;
    }
}