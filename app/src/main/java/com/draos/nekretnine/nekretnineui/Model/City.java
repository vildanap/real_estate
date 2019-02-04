package com.draos.nekretnine.nekretnineui.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Set;

public class City {
    @SerializedName("id")
    @Expose
    long id;

    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("locations")
    @Expose
    Set<Location> locations;

    public City(){}

    public City(String name) {
        this.name = name;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Location> getLocations() {
        return locations;
    }

    public void setLocations(Set<Location> locations) {
        this.locations = locations;
    }
}
