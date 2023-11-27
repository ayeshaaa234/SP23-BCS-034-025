package com.example.vehiclerentalsystem;

public class Van extends Vehicle{
    private int cargoCapacity;

    public Van(String type,String model, String name, double price, int cargoCapacity) {
        super("van",model, name, price);
        this.cargoCapacity = cargoCapacity;
    }

    @Override
    public String toString() {
        return String.format("Van - %s", super.toString());
    }


    public int getCargoCapacity() {
        return cargoCapacity;
    }

    public void setCargoCapacity(int cargoCapacity) {
        this.cargoCapacity = cargoCapacity;
    }
}
