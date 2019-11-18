package com.abdelrahman.footballleague.local.dataSource;

import android.content.Context;
import android.util.Log;


import androidx.annotation.NonNull;

import androidx.paging.PageKeyedDataSource;
import io.reactivex.Flowable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import io.reactivex.schedulers.Schedulers;

import com.abdelrahman.footballleague.local.TeamDao;
import com.abdelrahman.footballleague.local.TeamRoomDatabase;
import com.abdelrahman.footballleague.models.Team;

import java.util.List;

/**
 * Football League
 *
 * @author: Abdel-Rahman El-Shikh :) on 11/17/2019
 */
public class TeamDataSource extends PageKeyedDataSource<Integer, Team> {
    private TeamDao dao;
    private static final int PAGE_SIZE = 6;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    TeamDataSource(Context ctx) {
        TeamRoomDatabase teamRoomDatabase = TeamRoomDatabase.getDatabase(ctx);
        dao = teamRoomDatabase.teamDao();
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Team> callback) {
        Flowable<List<Team>> teams = dao.getAllTeams(0, params.requestedLoadSize);
        Disposable disposable = teams.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(teamsObserved -> {
                    int nextKey = teamsObserved.get(teamsObserved.size() - 1).getTeamId();
                    callback.onResult(teamsObserved, null, nextKey);
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Team> callback) {
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Team> callback) {
        Flowable<List<Team>> teams = dao.getAllTeams(params.key + 1, PAGE_SIZE);
        Disposable disposable = teams.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(teamsObserved -> {
                    if (!teamsObserved.isEmpty()) {
                        int nextKey = teamsObserved.get(teamsObserved.size() - 1).getTeamId();
                        callback.onResult(teamsObserved, nextKey);
                    }
                });
        compositeDisposable.add(disposable);


    }

}
