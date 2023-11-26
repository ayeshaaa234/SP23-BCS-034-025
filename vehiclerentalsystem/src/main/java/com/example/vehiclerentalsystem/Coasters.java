package com.example.vehiclerentalsystem;

public class Coasters extends Vehicle{
    private int seatingCapacity;

    public Coasters(String type,String model, String name, double price, int seatingCapacity) {
        super("coasters",model, name, price);
        this.seatingCapacity = seatingCapacity;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(int seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    @Override
    public String toString() {
        return "Coasters{" +
                "seatingCapacity=" + seatingCapacity +
                "} " + super.toString();
    }
}
