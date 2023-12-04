package com.example.vrs_project;


public class Van extends Vehicle{
    private int cargoCapacity;

    public Van(String type,String model, String name, double price, int cargoCapacity) {
        super("van",model, name, price);
        this.cargoCapacity = cargoCapacity;
    }

    @Override
    public String toString() {

        return String.format("%s,%s,%s,%f,%d","van",this.model,this.name,this.price,this.cargoCapacity);
    }
    public String toCSV() {

        return String.format("%s,%s,%s,%f,%d","van",this.model,this.name,this.price,this.cargoCapacity);
    }



    public int getCargoCapacity() {
        return cargoCapacity;
    }

    public void setCargoCapacity(int cargoCapacity) {
        this.cargoCapacity = cargoCapacity;
    }

}

