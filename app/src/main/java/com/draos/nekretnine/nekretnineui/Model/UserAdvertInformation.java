package com.draos.nekretnine.nekretnineui.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by amina on 2/9/2019.
 */

public class UserAdvertInformation {
    @SerializedName("numberOfAdverts")
    @Expose
    long numberOfAdverts;

    @SerializedName("numberOfFavorites")
    @Expose
    long numberOfFavorites;

    @SerializedName("numberOfAdvertViews")
    @Expose
    long numberOfAdvertViews;

    public UserAdvertInformation(){}

    public UserAdvertInformation(long numberOfAdverts, long numberOfAdvertViews, long numberOfFavorites) {
        this.numberOfAdverts = numberOfAdverts;
        this.numberOfAdvertViews = numberOfAdvertViews;
        this.numberOfFavorites = numberOfFavorites;
    }

    public long getNumberOfAdverts() {
        return numberOfAdverts;
    }
    public void setNumberOfAdverts(long numberOfAdverts) {
        this.numberOfAdverts = numberOfAdverts;
    }

    public long getNumberOfFavorites() {
        return numberOfFavorites;
    }
    public void setNumberOfFavorites(long numberOfFavorites) {
        this.numberOfFavorites = numberOfFavorites;
    }

    public long getNumberOfAdvertViews() {
        return numberOfAdvertViews;
    }
    public void setNumberOfAdvertViews(long numberOfAdvertViews) {
        this.numberOfAdvertViews = numberOfAdvertViews;
    }

}
