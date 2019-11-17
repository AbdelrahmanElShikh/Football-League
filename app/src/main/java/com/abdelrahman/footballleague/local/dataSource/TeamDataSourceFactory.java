package com.abdelrahman.footballleague.local.dataSource;

import android.content.Context;

import com.abdelrahman.footballleague.models.Team;

import androidx.paging.DataSource;

/**
 * Football League
 *
 * @author: Abdel-Rahman El-Shikh :) on 11/17/2019
 */
public class TeamDataSourceFactory extends DataSource.Factory<Integer, Team> {
    private Context ctx;
    private TeamItemKeyDataSource teamDataSource;

    public TeamDataSourceFactory(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public DataSource<Integer, Team> create() {
        if(teamDataSource == null){
            teamDataSource = new TeamItemKeyDataSource(ctx);
        }
        return teamDataSource;
    }
}
