package com.draos.nekretnine.nekretnineui.Model;

import java.util.List;

public class Advertise {
    private String title;
    private String image;
    private String price;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String rating) {
        this.price = rating;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", rating='" + price + '\'' +
                '}';
    }
}
