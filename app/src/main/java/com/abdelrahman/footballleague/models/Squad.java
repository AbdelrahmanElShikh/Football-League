package com.abdelrahman.footballleague.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Abdel-Rahman El-Shikh on 16-Nov-19.
 */
public class Squad {
    @SerializedName("id")
    @Expose
    @NonNull
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("position")
    @Expose
    private String position;
    @SerializedName("dateOfBirth")
    @Expose
    private String dateOfBirth;
    @SerializedName("countryOfBirth")
    @Expose
    private String countryOfBirth;
    @SerializedName("nationality")
    @Expose
    private String nationality;
    @SerializedName("shirtNumber")
    @Expose
    private String shirtNumber;
    @SerializedName("role")
    @Expose
    private String role;

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setCountryOfBirth(String countryOfBirth) {
        this.countryOfBirth = countryOfBirth;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setShirtNumber(String shirtNumber) {
        this.shirtNumber = shirtNumber;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }


    public String getPosition() {
        return position;
    }


    public String getDateOfBirth() {
        return dateOfBirth;
    }


    public String getCountryOfBirth() {
        return countryOfBirth;
    }


    public String getNationality() {
        return nationality;
    }


    public String getShirtNumber() {
        return shirtNumber;
    }


    public String getRole() {
        return role;
    }


}
