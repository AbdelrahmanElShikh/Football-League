package com.abdelrahman.footballleague.api;

import com.abdelrahman.footballleague.models.PremierLeague;
import com.abdelrahman.footballleague.models.Team;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author Abdel-Rahman El-Shikh on 15-Nov-19.
 */
public interface ApiService {

    //Get Premier League Teams
    @GET("competitions/2021/teams")
    Call<PremierLeague> getPremierLeagueTeams();

    //get specific team details by id.
    @GET("teams/{id}")
    Call<List<Team>> getTeamById(@Path("id") String teamId);
}
