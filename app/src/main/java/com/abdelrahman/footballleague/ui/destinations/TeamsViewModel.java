package com.abdelrahman.footballleague.ui.destinations;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.abdelrahman.footballleague.api.ApiResponse;
import com.abdelrahman.footballleague.local.TeamDao;
import com.abdelrahman.footballleague.local.TeamRoomDatabase;
import com.abdelrahman.footballleague.models.PremierLeague;
import com.abdelrahman.footballleague.models.Team;
import com.abdelrahman.footballleague.repositories.PremierLeagueRepository;

import java.util.List;

/**
 * @author Abdel-Rahman El-Shikh on 16-Nov-19.
 */
public class TeamsViewModel extends AndroidViewModel {
    private PremierLeagueRepository premierLeagueRepository;
    private TeamDao teamDao;
    private TeamRoomDatabase teamRoomDatabase;

    public TeamsViewModel(@NonNull Application application) {
        super(application);
        teamRoomDatabase = TeamRoomDatabase.getDatabase(application);
        teamDao = teamRoomDatabase.teamDao();
    }

    public void init() {
        premierLeagueRepository = PremierLeagueRepository.getInstance();
    }

    public void insertTeams(List<Team> teams){

    }

    public LiveData<ApiResponse<PremierLeague>> getPremierLeagueTeams(){
        return premierLeagueRepository.getPremierLeagueTeams();
    }
}
