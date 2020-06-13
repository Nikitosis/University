package com.del;

public class BmwFactory implements AbstractFactory{
    @Override
    public Car car() {
        return new BmwCar();
    }

    @Override
    public SportCar sportCar() {
        return new BmwSportCar();
    }
}
