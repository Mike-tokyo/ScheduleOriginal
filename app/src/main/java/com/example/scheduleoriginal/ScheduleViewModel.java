package com.example.scheduleoriginal;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ScheduleViewModel extends AndroidViewModel {
    private ScheduleRepository mRepository;
    private LiveData<List<Schedule>> mAllSchedule;

    public ScheduleViewModel(Application application){
        super(application);
        mRepository = new ScheduleRepository(application);
        mAllSchedule = mRepository.getAllSchedule();
    }
    
    LiveData<List<Schedule>> getAllSchedule(){ return mAllSchedule; }

    public void insert(Schedule schedule){ mRepository.insert(schedule); }

    public void update(Schedule schedule){ mRepository.update(schedule);}

    public void deleteSchedule(Schedule schedule) {mRepository.deleteSchedule(schedule);}

    public void deleteAll() {mRepository.deleteAll();}
}
