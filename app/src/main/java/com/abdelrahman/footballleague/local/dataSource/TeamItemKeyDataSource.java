package com.abdelrahman.footballleague.local.dataSource;

import android.content.Context;

import com.abdelrahman.footballleague.local.TeamDao;
import com.abdelrahman.footballleague.local.TeamRoomDatabase;
import com.abdelrahman.footballleague.models.Team;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.ItemKeyedDataSource;

/**
 * Football League
 *
 * @author: Abdel-Rahman El-Shikh :) on 11/17/2019
 */
public class TeamItemKeyDataSource extends ItemKeyedDataSource<Integer, Team> {
    private TeamDao dao;
    private TeamRoomDatabase teamRoomDatabase;

    public TeamItemKeyDataSource(Context context) {
        teamRoomDatabase = TeamRoomDatabase.getDatabase(context);
        dao = teamRoomDatabase.teamDao();
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Team> callback) {
        LiveData<List<Team>> teamsLiveData = dao.getAllTeams(0,params.requestedLoadSize);
        callback.onResult(Objects.requireNonNull(teamsLiveData.getValue()),0,teamsLiveData.getValue().size());
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Team> callback) {
        LiveData<List<Team>> teamsLiveData = dao.getAllTeams(params.key,params.requestedLoadSize);
        callback.onResult(Objects.requireNonNull(teamsLiveData.getValue()));
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Team> callback) {

    }

    @NonNull
    @Override
    public Integer getKey(@NonNull Team item) {
        return item.getId();
    }
}
