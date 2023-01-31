package com.example.scheduleoriginal;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "schedule_table")
public class Schedule {
    //データベース本体
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="year")
    private int mYear;

    @ColumnInfo(name="month")
    private int mMonth;

    @ColumnInfo(name="day")
    private int mDay;

    @ColumnInfo(name="hour")
    private int mHour;

    @ColumnInfo(name="minute")
    private int mMinute;

    @NonNull
    @ColumnInfo(name = "about")
    private String mAbout;

    public Schedule(int year,int month,int day,int hour,int minute,@NonNull String about){
        this.mYear=year;
        this.mMonth=month;
        this.mDay=day;
        this.mHour=hour;
        this.mMinute=minute;
        this.mAbout=about;
    }

    @Ignore
    public Schedule(int id,int year,int month,int day,int hour,int minute,@NonNull String about){
        this.id=id;
        this.mYear=year;
        this.mMonth=month;
        this.mDay=day;
        this.mHour=hour;
        this.mMinute=minute;
        this.mAbout=about;
    }

    public void setId(int id){this.id=id;}

    public int getId(){return this.id;}

    public int getYear(){return this.mYear;}

    public int getMonth(){return this.mMonth;}

    public int getDay(){return this.mDay;}

    public int getHour(){return this.mHour;}

    public int getMinute(){return this.mMinute;}

    public String getAbout(){return this.mAbout;}
}
