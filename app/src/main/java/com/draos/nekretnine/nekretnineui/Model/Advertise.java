package com.draos.nekretnine.nekretnineui.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Advertise {
    @SerializedName("id")
    @Expose
    long id;

    @SerializedName("title")
    @Expose
    String title;

    @SerializedName("description")
    @Expose
    String description;

    @SerializedName("price")
    @Expose
    double price;

    @SerializedName("area")
    @Expose
    double area;

    @SerializedName("advertType")
    @Expose
    String advertType;

    @SerializedName("propertyType")
    @Expose
    String propertyType;

    @SerializedName("viewsCount")
    @Expose
    long viewsCount;

    @SerializedName("numberOfRooms")
    @Expose
    long numberOfRooms;

    @SerializedName("address")
    @Expose
    String address;

    @SerializedName("user")
    @Expose
    User user;

    @SerializedName("location")
    @Expose
    Location location;

    public Advertise(String title, String description, double price, double area, String advertType, String propertyType, long viewsCount, long numberOfRooms, String address, User user, Location location) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.area = area;
        this.advertType = advertType;
        this.propertyType = propertyType;
        this.viewsCount = viewsCount;
        this.numberOfRooms = numberOfRooms;
        this.address = address;
        this.user = user;
        this.location = location;
    }

    public Advertise(){}
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getAdvertType() {
        return advertType;
    }

    public void setAdvertType(String advertType) {
        this.advertType = advertType;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public long getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(long viewsCount) {
        this.viewsCount = viewsCount;
    }

    public long getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(long numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
