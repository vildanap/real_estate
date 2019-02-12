package com.draos.nekretnine.nekretnineui.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdvertSearch {
    @SerializedName("advertType")
    @Expose
    private long advertType;
    @SerializedName("minPrice")
    @Expose
    private long minPrice;
    @SerializedName("maxPrice")
    @Expose
    private long maxPrice;
    @SerializedName("cityId")
    @Expose
    private long cityId;
    @SerializedName("locationId")
    @Expose
    private long locationId;


    public AdvertSearch(){}

    public AdvertSearch(long advertType, long minPrice, long maxPrice, long cityId, long locationId, long numberOfRooms) {
        this.advertType = advertType;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.cityId = cityId;
        this.locationId = locationId;
        this.numberOfRooms = numberOfRooms;
    }


    public long getAdvertType() {
        return advertType;
    }

    public void setAdvertType(long advertType) {
        this.advertType = advertType;
    }

    public long getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(long minPrice) {
        this.minPrice = minPrice;
    }

    public long getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(long maxPrice) {
        this.maxPrice = maxPrice;
    }

    public long getCityId() {
        return cityId;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

    public long getLocationId() {
        return locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    public long getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(long numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    private long numberOfRooms;

}
