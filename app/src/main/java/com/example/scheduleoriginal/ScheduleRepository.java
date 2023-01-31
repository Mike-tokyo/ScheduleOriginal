package com.example.scheduleoriginal;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ScheduleRepository {
    private ScheduleDao mScheduleDao;
    private LiveData<List<Schedule>> mAllSchedule;

    ScheduleRepository(Application application){
        ScheduleRoomDatabase db = ScheduleRoomDatabase.getDatabase(application);
        mScheduleDao = db.scheduleDao();//getterからDaoを取得
        mAllSchedule = mScheduleDao.getAllSchedule();
    }

    //ViewModelに全スケジュールを返す
    LiveData<List<Schedule>> getAllSchedule(){
        return mAllSchedule;
    }

    public void insert(Schedule schedule){
        new insertAsyncTask(mScheduleDao).execute(schedule);
    }

    public void update(Schedule schedule) { new updateScheduleAsyncTask(mScheduleDao).execute(schedule);}

    public void deleteSchedule(Schedule schedule){ new deleteScheduleAsyncTask(mScheduleDao).execute(schedule); }

    public void deleteAll() { new deleteAllScheduleAsyncTask(mScheduleDao).execute(); }

    private static class insertAsyncTask extends AsyncTask<Schedule, Void,Void>{
        private ScheduleDao mAsyncTaskDao;

        insertAsyncTask(ScheduleDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Schedule... params){
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class updateScheduleAsyncTask extends  AsyncTask<Schedule, Void, Void> {
        private ScheduleDao mAsyncTaskDao;

        updateScheduleAsyncTask(ScheduleDao dao){mAsyncTaskDao = dao;}

        @Override
        protected Void doInBackground(final Schedule... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

    private static class deleteScheduleAsyncTask extends AsyncTask<Schedule, Void, Void> {
        private ScheduleDao mAsyncTaskDao;

        deleteScheduleAsyncTask(ScheduleDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Schedule... params) {
            mAsyncTaskDao.deleteSchedule(params[0]);
            return null;
        }
    }

    private static class deleteAllScheduleAsyncTask extends AsyncTask<Void, Void, Void> {
        private ScheduleDao mAsyncTaskDao;

        deleteAllScheduleAsyncTask(ScheduleDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids){
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
}
