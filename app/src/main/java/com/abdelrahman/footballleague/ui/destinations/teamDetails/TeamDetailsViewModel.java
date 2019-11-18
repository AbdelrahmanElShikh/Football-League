package com.abdelrahman.footballleague.ui.destinations.teamDetails;

import com.abdelrahman.footballleague.api.ApiResponse;
import com.abdelrahman.footballleague.models.Team;
import com.abdelrahman.footballleague.repositories.PremierLeagueRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

/**
 * Football League
 *
 * @author: Abdel-Rahman El-Shikh :) on 11/18/2019
 */
public class TeamDetailsViewModel extends ViewModel {
    private PremierLeagueRepository premierLeagueRepository;

    void init() {
        premierLeagueRepository = PremierLeagueRepository.getInstance();
    }
    LiveData<ApiResponse<Team>> getTeamById(Integer id) {
        return premierLeagueRepository.getTeamById(id);
    }
}
