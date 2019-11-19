package com.abdelrahman.footballleague.local.teamDatabase;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.abdelrahman.footballleague.models.Team;

import java.util.List;

import io.reactivex.Flowable;

/**
 * @author Abdel-Rahman El-Shikh on 17-Nov-19.
 */
@Dao
public interface TeamDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTeams(List<Team> teams);

    @Query("SELECT * FROM teams WHERE teamId >= :id LIMIT :size")
    Flowable<List<Team>> getAllTeams(int id, int size);

    @Query("DELETE FROM teams")
    void deleteAllTeams();
}
