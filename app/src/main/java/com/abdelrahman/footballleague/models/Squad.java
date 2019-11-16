package com.abdelrahman.footballleague.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Abdel-Rahman El-Shikh on 16-Nov-19.
 */
public class Squad {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("position")
    @Expose
    private Object position;
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
    private Object shirtNumber;
    @SerializedName("role")
    @Expose
    private String role;

    public Integer getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public Object getPosition() {
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


    public Object getShirtNumber() {
        return shirtNumber;
    }


    public String getRole() {
        return role;
    }


}
