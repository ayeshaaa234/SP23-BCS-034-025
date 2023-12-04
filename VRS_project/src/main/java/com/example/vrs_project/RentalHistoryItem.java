package com.example.vrs_project;


import java.io.Serializable;
import java.util.Date;

public class RentalHistoryItem implements Serializable {
    private String rentedBy;
    private String vehicleType;
    private String model;
    private String name;
    private Date dateRented;
    private Date dateReturned;


    public RentalHistoryItem(String rentedBy, String vehicleType, String model, String name, Date dateRented , Date dateReturned) {
        this.rentedBy = rentedBy;
        this.vehicleType = vehicleType;
        this.model = model;
        this.name = name;
        this.dateRented = dateRented;
        this.dateReturned=dateReturned;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%d,%d",this.rentedBy,this.vehicleType,this.model,this.name
        ,this.dateRented,this.dateReturned);
    }
    public String toCSV() {
        return String.format("%s,%s,%s,%s,%d,%d",this.rentedBy,this.vehicleType,this.model,this.name
                ,this.dateRented,this.dateReturned);
    }
    public String getRentedBy() {
        return rentedBy;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getModel() {
        return model;
    }

    public String getName() {
        return name;
    }

    public Date getDateRented() {
        return dateRented;
    }

    public Date getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(Date dateReturned) {
        this.dateReturned = dateReturned;
    }
}
