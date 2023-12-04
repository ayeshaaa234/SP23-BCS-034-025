package com.example.vrs_project;


import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class RentalRecord implements Serializable {
    private String vehicleType;
    private String rentedBy;
    private Date dateRented;
    private String model;
    private String name;

    private Vehicle vehicle;
    private Date dateReturned;


    public RentalRecord(String vehicleType, String rentedBy, Date dateRented, Date dateReturned) {
        this.vehicleType = vehicleType;
        this.rentedBy = rentedBy;
        this.dateRented = dateRented;
        this.dateReturned=null;
    }

    public RentalRecord(String vehicleType, String rentedBy, String model, String name,Date dateRented, Date dateReturned) {
        this.vehicleType = vehicleType;
        this.rentedBy = rentedBy;
        this.dateRented = dateRented;
        this.dateReturned=null;
        this.model=model;
        this.name=name;
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

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }


    public Date getDateReturned() {

        return dateReturned;
    }

    public void setDateReturned(Date dateReturned) {
        this.dateReturned = dateReturned;
    }

    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        return String.format("%s,%s,%s,%s",this.vehicleType,this.rentedBy,dateFormat.format(this.dateRented)
                ,dateFormat.format(this.dateReturned));
    }
    public String toCSV() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        return String.format("%s,%s,%s,%s,%s,%s",this.vehicleType,this.rentedBy,this.model,this.name,dateFormat.format(this.dateRented)
                ,this.dateReturned!=null ? dateFormat.format(this.dateReturned):null);


    }
}
