package com.abdelrahman.footballleague.local;

import androidx.room.Dao;
import androidx.room.Insert;

import com.abdelrahman.footballleague.models.Team;

import java.util.List;

/**
 * @author Abdel-Rahman El-Shikh on 17-Nov-19.
 */
@Dao
public interface TeamDao {
    @Insert
    void insertTeams(List<Team> teams);
}
