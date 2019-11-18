package com.abdelrahman.footballleague.api;

import com.abdelrahman.footballleague.models.PremierLeague;
import com.abdelrahman.footballleague.models.Team;



import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * @author Abdel-Rahman El-Shikh on 15-Nov-19.
 */
public interface ApiService {

    //Get Premier League Teams
    @Headers("X-Auth-Token: " + "ae449e21e9524d928dadbdc85df4022c")
    @GET("competitions/2021/teams")
    Call<PremierLeague> getPremierLeagueTeams();

    //get specific team details by id.
    @Headers("X-Auth-Token: " + "ae449e21e9524d928dadbdc85df4022c")
    @GET("teams/{id}")
    Call<Team> getTeamById(@Path("id") Integer teamId);
}
