package com.del;

public class AudiFactory implements AbstractFactory {
    @Override
    public Car car() {
        return new AudiCar();
    }

    @Override
    public SportCar sportCar() {
        return new AudiSportCar();
    }
}
