package com.example.vrs_project;


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
        //return String.format("Car - %s", super.toString());
        return String.format("%s,%s,%s,%f,%d","car",this.model,this.name,this.price,this.numberOfDoors);
    }
    public String toCSV() {
        //return String.format("Car - %s", super.toString());
        return String.format("%s,%s,%s,%f,%d","car",this.model,this.name,this.price,this.numberOfDoors);
    }

}

