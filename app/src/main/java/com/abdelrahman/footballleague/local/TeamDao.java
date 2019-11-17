package com.abdelrahman.footballleague.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.abdelrahman.footballleague.models.Team;

import java.util.List;

/**
 * @author Abdel-Rahman El-Shikh on 17-Nov-19.
 */
@Dao
public interface TeamDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTeams(List<Team> teams);

    @Query("SELECT * FROM teams WHERE id >= :id LIMIT :size")
    LiveData<List<Team>> getAllTeams(int id,int size);

    @Query("DELETE FROM teams")
    void deleteAllTeams();
}
