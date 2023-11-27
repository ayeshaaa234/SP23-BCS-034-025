package com.example.vehiclerentalsystem;

import java.util.ArrayList;
import java.util.Date;

public class Vehicle {
   private String type;
    private String model;
    private String name;
    private double price;
    private Date dateRented;
    private Date dateReturned;

    public Vehicle(String type, String model, String name, double price) {
        this.type=type;
        this.model = model;
        this.name = name;
        this.price = price;
    }

    // Getters and Setters for common attributes

    public Date getDateRented() {
        return dateRented;
    }

    public void setDateRented(Date dateRented) {
        this.dateRented = dateRented;
    }

    public Date getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(Date dateReturned) {
        this.dateReturned = dateReturned;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    @Override
    public String toString() {
        return String.format("Type: %s, Model: %s, Name: %s, Price: %.2f", type, model, name, price);
    }

}
