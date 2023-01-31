package com.example.scheduleoriginal;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Schedule.class},version = 1,exportSchema = false)
public abstract class ScheduleRoomDatabase extends RoomDatabase {
    private static ScheduleRoomDatabase INSTANCE;

    public static ScheduleRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (ScheduleRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext()
                            ,ScheduleRoomDatabase.class,"schedule_database")
                            .fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract ScheduleDao scheduleDao();
}
