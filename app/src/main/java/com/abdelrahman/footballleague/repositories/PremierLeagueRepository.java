package com.abdelrahman.footballleague.repositories;

import androidx.lifecycle.LiveData;

import com.abdelrahman.footballleague.api.ApiResponse;
import com.abdelrahman.footballleague.api.ApiService;
import com.abdelrahman.footballleague.api.RequestHandler;
import com.abdelrahman.footballleague.api.RetrofitBuilder;
import com.abdelrahman.footballleague.models.PremierLeague;
import com.abdelrahman.footballleague.models.Team;

import java.util.List;

import retrofit2.Call;

/**
 * @author Abdel-Rahman El-Shikh on 16-Nov-19.
 */
public class PremierLeagueRepository {
    private static final String TAG = "PremierLeagueRepository";
    public static  PremierLeagueRepository premierLeagueRepository;
    private ApiService apiService = RetrofitBuilder.createService(ApiService.class);


    public static PremierLeagueRepository getInstance(){
        if(premierLeagueRepository == null)
            premierLeagueRepository = new PremierLeagueRepository();
        return premierLeagueRepository;
    }

    public LiveData<ApiResponse<PremierLeague>> getPremierLeagueTeams(){
        RequestHandler<PremierLeague> requestHandler = new RequestHandler<PremierLeague>() {
            @Override
            public Call<PremierLeague> makeRequest() {
                return apiService.getPremierLeagueTeams();
            }
        };
        requestHandler.doRequest();
        return requestHandler.getApiResponseLiveData();
    }

    public LiveData<ApiResponse<List<Team>>> getTeamById(String teamId){
        RequestHandler<List<Team>> requestHandler = new RequestHandler<List<Team>>() {
            @Override
            public Call<List<Team>> makeRequest() {
                return apiService.getTeamById(teamId);
            }
        };
        requestHandler.doRequest();
        return requestHandler.getApiResponseLiveData();
    }
}
