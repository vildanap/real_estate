package com.draos.nekretnine.nekretnineui.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Location {
    @SerializedName("id")
    @Expose
    long id;

    @SerializedName("settlement")
    @Expose
    String settlement;

    @SerializedName("city")
    @Expose
    City city;

    public Location(){}

    public Location(String settlement, City city) {
        this.settlement = settlement;
        this.city = city;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSettlement() {
        return settlement;
    }

    public void setSettlement(String settlement) {
        this.settlement = settlement;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
