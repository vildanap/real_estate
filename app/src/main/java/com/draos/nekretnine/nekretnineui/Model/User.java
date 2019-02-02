package com.draos.nekretnine.nekretnineui.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    @Expose
    long id;

    @SerializedName("firstName")
    @Expose
    String firstName;

    @SerializedName("lastName")
    @Expose
    String lastName;

    @SerializedName("username")
    @Expose
    String username;

    @SerializedName("password")
    @Expose
    String password;

    @SerializedName("email")
    @Expose
    String email;

    @SerializedName("telephone")
    @Expose
    String telephone;

    public User(){}

    public User(String username, String password, String firstName, String lastName, String email, String telephone) {
        this.username = username;
        this.password=password;
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.telephone=telephone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return firstName;
    }
}