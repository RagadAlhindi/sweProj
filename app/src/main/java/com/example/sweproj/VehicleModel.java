package com.example.sweproj;

import android.graphics.Bitmap;

public class VehicleModel {

    private int id;
    private String plateNo;
    private String model;
    private int year;
    private String type;
    private String city;
    private String description;
    private int rent;

    private Bitmap img;


    public VehicleModel(int id, String plateNo, String model, int year, String type, String city, String description, int rent, Bitmap img) {
        this.id = id;
        this.plateNo = plateNo;
        this.model = model;
        this.year = year;
        this.type = type;
        this.city = city;
        this.description = description;
        this.rent = rent;
        this.img = img;
    }

    @Override
    public String toString() {
        return "VehicleModel{" +
                "id=" + id +
                ", plateNo='" + plateNo + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", type='" + type + '\'' +
                ", city='" + city + '\'' +
                ", description='" + description + '\'' +
                ", rent=" + rent +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public String getType() {
        return type;
    }

    public String getCity() {
        return city;
    }

    public String getDescription() {
        return description;
    }


    public int getRent() {
        return rent;
    }

    public Bitmap getImg() {
        return img;
    }
}
