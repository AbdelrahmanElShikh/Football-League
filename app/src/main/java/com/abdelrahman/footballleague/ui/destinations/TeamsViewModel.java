package com.abdelrahman.footballleague.ui.destinations;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.abdelrahman.footballleague.api.ApiResponse;
import com.abdelrahman.footballleague.local.TeamDao;
import com.abdelrahman.footballleague.local.TeamRoomDatabase;
import com.abdelrahman.footballleague.local.dataSource.TeamDataSourceFactory;
import com.abdelrahman.footballleague.models.PremierLeague;
import com.abdelrahman.footballleague.models.Team;
import com.abdelrahman.footballleague.repositories.PremierLeagueRepository;

import java.util.List;

/**
 * @author Abdel-Rahman El-Shikh on 16-Nov-19.
 */
public class TeamsViewModel extends AndroidViewModel {
    private static final String TAG = "TeamsViewModel";
    private PremierLeagueRepository premierLeagueRepository;
    public final LiveData<PagedList<Team>> teamsLiveData;
    private MutableLiveData<ApiResponse<PremierLeague>> premierLeague;
    private TeamDao teamDao;
    private TeamRoomDatabase teamRoomDatabase;

    public TeamsViewModel(@NonNull Application application) {
        super(application);
        TeamDataSourceFactory factory = new TeamDataSourceFactory(application);
        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(6)
                .setPageSize(6).build();
        teamsLiveData = new LivePagedListBuilder<>(factory, config).build();
        teamRoomDatabase = TeamRoomDatabase.getDatabase(application);
        teamDao = teamRoomDatabase.teamDao();
    }

    public void init() {
        premierLeagueRepository = PremierLeagueRepository.getInstance();
    }

    public void insertTeams(List<Team> teams) {
        insertAsync(teams);
    }

    public LiveData<PagedList<Team>> getTeamsLiveData() {
        return teamsLiveData;
    }

    public void deleteAndInsertNewTeams(List<Team> teams) {
        deleteAndInsertNewTeamsAsync(teams);
    }

    private void deleteAndInsertNewTeamsAsync(List<Team> teams) {
        new Thread(() -> {
            try {
                teamDao.deleteAllTeams();
                insertTeams(teams);
            } catch (Exception e) {
                Log.e(TAG, "Delete run: " + e.getLocalizedMessage());
            }

        }).start();
    }

    private void insertAsync(List<Team> teams) {
        new Thread(() -> {
            try {
                teamDao.insertTeams(teams);
                Log.e(TAG, "insertTeams: inserted");
            } catch (Exception e) {
                Log.e(TAG, "Insert run: " + e.getLocalizedMessage());
            }
        }).start();
    }

    public LiveData<ApiResponse<PremierLeague>> getPremierLeagueTeams() {
        premierLeague = premierLeagueRepository.getPremierLeagueTeams();
        return premierLeague;
    }


}
