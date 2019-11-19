package com.abdelrahman.footballleague.local.teamDatabase.dataSource;

import android.content.Context;

import androidx.paging.DataSource;

import com.abdelrahman.footballleague.models.Team;

/**
 * Football League
 *
 * @author: Abdel-Rahman El-Shikh :) on 11/17/2019
 */
public class TeamDataSourceFactory extends DataSource.Factory<Integer, Team> {
    private Context ctx;
    private TeamDataSource teamDataSource;

    public TeamDataSourceFactory(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public DataSource<Integer, Team> create() {
        if(teamDataSource == null){
            teamDataSource = new TeamDataSource(ctx);
        }
        return teamDataSource;
    }
}
