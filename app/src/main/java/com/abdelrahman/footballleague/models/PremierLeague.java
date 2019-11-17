package com.abdelrahman.footballleague.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Abdel-Rahman El-Shikh on 16-Nov-19.
 */
public class PremierLeague {

    @SerializedName("count")
    @Expose
    private Integer count;

    @SerializedName("teams")
    @Expose
    private List<Team> teams = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
