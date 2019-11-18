package com.abdelrahman.footballleague.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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

    private static  PremierLeagueRepository premierLeagueRepository;
    private ApiService apiService = RetrofitBuilder.createService(ApiService.class);


    public static PremierLeagueRepository getInstance(){
        if(premierLeagueRepository == null)
            premierLeagueRepository = new PremierLeagueRepository();
        return premierLeagueRepository;
    }

    public MutableLiveData<ApiResponse<PremierLeague>> getPremierLeagueTeams(){
        RequestHandler<PremierLeague> requestHandler = new RequestHandler<PremierLeague>() {
            @Override
            public Call<PremierLeague> makeRequest() {
                return apiService.getPremierLeagueTeams();
            }
        };
        requestHandler.doRequest();
        return requestHandler.getApiResponseLiveData();
    }

    public LiveData<ApiResponse<Team>> getTeamById(Integer teamId){
        RequestHandler<Team> requestHandler = new RequestHandler<Team>() {
            @Override
            public Call<Team> makeRequest() {
                return apiService.getTeamById(teamId);
            }
        };
        requestHandler.doRequest();
        return requestHandler.getApiResponseLiveData();
    }
}
