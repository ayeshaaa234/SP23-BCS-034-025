package com.example.vehiclerentalsystem;

import java.util.Date;

public class RentalRecord {
    private String vehicleType;
    private String rentedBy;
    private Date dateRented;
    private String model;
    private String name;
    public RentalRecord(String vehicleType, String rentedBy, Date dateRented) {
        this.vehicleType = vehicleType;
        this.rentedBy = rentedBy;
        this.dateRented = dateRented;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getRentedBy() {
        return rentedBy;
    }


    public Date getDateRented() {
        return dateRented;
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
}
