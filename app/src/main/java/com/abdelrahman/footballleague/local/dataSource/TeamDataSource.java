package com.abdelrahman.footballleague.local.dataSource;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

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
    private static final String TAG = "TeamDataSource";
    private TeamDao dao;
    private TeamRoomDatabase teamRoomDatabase;

    private static final int FIRST_PAGE = 1;
    public static final int PAGE_SIZE = 6;

    public TeamDataSource(Context ctx) {

        teamRoomDatabase = TeamRoomDatabase.getDatabase(ctx);
        dao = teamRoomDatabase.teamDao();
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Team> callback) {
        List<Team> teams = dao.getAllTeams(0,params.requestedLoadSize);

 //       this is required to handle first request after db is created or app is installed
        int noOfTries = 0;


        while (teams.size() == 0){
            teams = dao.getAllTeams(0,params.requestedLoadSize);
            noOfTries++;
            if(noOfTries == 6) break;
            try {
                Thread.sleep(500);
            }catch (InterruptedException  e){}
        }
        Log.e(TAG, "loadInitial: "+teams.size() );
        callback.onResult(teams,null,FIRST_PAGE+1);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Team> callback) {
//        List<Team> teams = dao.getAllTeams(params.key,PAGE_SIZE);
//        //if the current page is greater than one
//        //we are decrementing the page number
//        //else there is no previous page
//        Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;
//        if(teams!=null)
//        callback.onResult(teams, adjacentKey);
        Log.e(TAG, "loadBefore: " );

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Team> callback) {
        List<Team> teams = dao.getAllTeams(params.key,PAGE_SIZE);
        if(teams!=null){
            int nextKey = params.key+ teams.size();
            //Log.e(TAG, "loadAfter: params.key =  "+params.key+" , nextKey " +nextKey);
            callback.onResult(teams, nextKey);
        }



    }

}
