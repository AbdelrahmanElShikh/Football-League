package com.abdelrahman.footballleague.models;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


/**
 * @author Abdel-Rahman El-Shikh on 16-Nov-19.
 */
@Entity(tableName = "teams")
public class Team {
    @SerializedName("id")
    @Expose
    @PrimaryKey
    @NonNull
    private Integer teamId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("shortName")
    @Expose
    private String shortName;
    @SerializedName("tla")
    @Expose
    private String tla;
    @SerializedName("crestUrl")
    @Expose
    private String crestUrl;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("founded")
    @Expose
    private Integer founded;
    @SerializedName("clubColors")
    @Expose
    private String clubColors;
    @SerializedName("venue")
    @Expose
    private String venue;
    @Ignore
    @SerializedName("squad")
    @Expose
    private List<Squad> squad = null;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getTla() {
        return tla;
    }

    public void setTla(String tla) {
        this.tla = tla;
    }

    public String getCrestUrl() {
        return crestUrl;
    }

    public void setCrestUrl(String crestUrl) {
        this.crestUrl = crestUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getFounded() {
        return founded;
    }

    public void setFounded(Integer founded) {
        this.founded = founded;
    }

    public String getClubColors() {
        return clubColors;
    }

    public void setClubColors(String clubColors) {
        this.clubColors = clubColors;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public List<Squad> getSquad() {
        return squad;
    }

    @NonNull
    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(@NonNull Integer teamId) {
        this.teamId = teamId;
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Team)) return false;
        else {
            Team team = (Team) o;
            return this.teamId.equals(team.teamId);
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                '}';
    }


}
