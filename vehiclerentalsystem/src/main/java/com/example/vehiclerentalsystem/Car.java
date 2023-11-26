package com.example.vehiclerentalsystem;

public class Car extends Vehicle{
    private int numberOfDoors;

    public Car(String type, String model, String name, double price, int numberOfDoors) {
        super("car",model, name, price);
        this.numberOfDoors = numberOfDoors;
    }


    public int getNumberOfDoors() {
        return numberOfDoors;
    }

    public void setNumberOfDoors(int numberOfDoors) {
        this.numberOfDoors = numberOfDoors;
    }

    @Override
    public String toString() {
        return "Car{" +
                "numberOfDoors=" + numberOfDoors +
                "} " + super.toString();
    }
}
