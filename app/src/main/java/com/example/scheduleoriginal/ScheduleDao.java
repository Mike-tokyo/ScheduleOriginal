package com.example.scheduleoriginal;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ScheduleDao {
    //製作中
    @Insert
    void insert(Schedule schedule);

    @Update
    void update(Schedule... schedule);

    @Delete
    void deleteSchedule(Schedule schedule);

    @Query("DELETE FROM schedule_table")
    void deleteAll();

    @Query("SELECT * from schedule_table ORDER BY year ASC,month ASC,day ASC,hour ASC,minute ASC")
    LiveData<List<Schedule>> getAllSchedule();
}
