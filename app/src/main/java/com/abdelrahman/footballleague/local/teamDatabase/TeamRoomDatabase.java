package com.abdelrahman.footballleague.local.teamDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.abdelrahman.footballleague.models.Team;

/**
 * @author Abdel-Rahman El-Shikh on 17-Nov-19.
 * Singilton Class
 */
@Database(entities = Team.class,version = 1)
public abstract class TeamRoomDatabase extends RoomDatabase {
    public abstract TeamDao teamDao();

    private static volatile TeamRoomDatabase teamRoomInstance;
    public static TeamRoomDatabase getDatabase(final Context context){
        if(teamRoomInstance == null){
            synchronized (TeamRoomDatabase.class){
                if(teamRoomInstance==null){
                    teamRoomInstance = Room.databaseBuilder(context.getApplicationContext(),TeamRoomDatabase.class,"team_database")
                            .build();
                }
            }
        }
        return teamRoomInstance;
    }
}
