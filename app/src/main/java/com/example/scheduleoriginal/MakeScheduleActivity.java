package com.example.scheduleoriginal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MakeScheduleActivity extends AppCompatActivity {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private EditText mEditAbout;
    private RadioButton mRadioDay;
    private RadioButton mRadioTime;
    private int id = -9999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_schedule);
        mEditAbout = findViewById(R.id.editText_about);

        final Bundle extras = getIntent().getExtras();
        if(extras != null){
            id = extras.getInt("idExtUpdate",-9999);
            mEditAbout.setText(extras.getString("aboutExtUpdate"));
        }
    }

    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }

    public void processDatePickerResult(int year, int month, int day) {
        this.year=year;
        this.month=month+1;
        this.day=day;
    }

    public void showTimePicker(View view) {
        DialogFragment newFragment=new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(),"timePicker");
    }

    public void processTimePickerResult(int hour,int minute){
        this.hour=hour;
        this.minute=minute;
    }

    public void saveToSchedule(View view) {
        mRadioDay=findViewById(R.id.radio_setDay);
        mRadioTime=findViewById(R.id.radio_setTime);
        Intent replyIntent = new Intent();
        if(TextUtils.isEmpty(mEditAbout.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
        } else {
            if(mRadioDay.isChecked()) {
                replyIntent.putExtra("yearExt",year);
                replyIntent.putExtra("monthExt",month);
                replyIntent.putExtra("dayExt",day);
            }
            if(mRadioTime.isChecked()) {
                replyIntent.putExtra("hourExt",hour);
                replyIntent.putExtra("minuteExt",minute);
            }
            if(id!=-9999){//MainActivityから更新用にidが送られてきたとき
                replyIntent.putExtra("idExt",id);
            }
            replyIntent.putExtra("aboutExt",mEditAbout.getText().toString());
            setResult(RESULT_OK ,replyIntent);
        }
        finish();
    }
}