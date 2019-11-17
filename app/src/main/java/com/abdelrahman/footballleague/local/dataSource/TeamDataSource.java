package com.abdelrahman.footballleague.local.dataSource;

import android.content.Context;

import com.abdelrahman.footballleague.local.TeamDao;
import com.abdelrahman.footballleague.local.TeamRoomDatabase;
import com.abdelrahman.footballleague.models.Team;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.PageKeyedDataSource;

/**
 * Football League
 *
 * @author: Abdel-Rahman El-Shikh :) on 11/17/2019
 */
public class TeamDataSource extends PageKeyedDataSource<Integer, Team> {
    private TeamDao dao;
    private TeamRoomDatabase teamRoomDatabase;

    public TeamDataSource(Context ctx) {
        teamRoomDatabase = TeamRoomDatabase.getDatabase(ctx);
        dao = teamRoomDatabase.teamDao();
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Team> callback) {
        LiveData<List<Team>> teamsLiveData = dao.getAllTeams(0,params.requestedLoadSize);

        //this is required to handle first request after db is created or app is installed
        int noOfTries = 0;

        while (Objects.requireNonNull(teamsLiveData.getValue()).size() == 0){
            teamsLiveData = dao.getAllTeams(0,params.requestedLoadSize);
            noOfTries++;
            if(noOfTries == 6) break;
            try {
                Thread.sleep(500);
            }catch (InterruptedException  e){}
        }
        callback.onResult(Objects.requireNonNull(teamsLiveData.getValue()),null,teamsLiveData.getValue().size()+1);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Team> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Team> callback) {
        LiveData<List<Team>> teamsLiveData = dao.getAllTeams(params.key,params.requestedLoadSize);
        int nextKey = params.key+ Objects.requireNonNull(teamsLiveData.getValue()).size();
        callback.onResult(teamsLiveData.getValue(), nextKey);
    }
}
