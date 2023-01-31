package com.example.scheduleoriginal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import androidx.lifecycle.ViewModelProviders;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.scheduleoriginal.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    public static final int MAKE_SCHEDULE_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_SCHEDULE_ACTIVITY_REQUEST_CODE = 2;
    private ScheduleViewModel mScheduleViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //メイン画面
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MakeScheduleActivity.class);
                startActivityForResult(intent, MAKE_SCHEDULE_ACTIVITY_REQUEST_CODE);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final ScheduleListAdapter adapter = new ScheduleListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mScheduleViewModel = ViewModelProviders.of(this).get(ScheduleViewModel.class);
        mScheduleViewModel.getAllSchedule().observe(this, new Observer<List<Schedule>>() {
            @Override
            public void onChanged(@Nullable final List<Schedule> schedules) {
                adapter.setSchedule(schedules);
            }
        });

        //スワイプ時にRecyclerViewから削除する処理
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback
                (0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView,RecyclerView.ViewHolder viewHolder,RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Schedule mySchedule = adapter.getScheduleAtPosition(position);
                buildDialogAlert("Confirmation","Do you want to clear this schedule?",2,mySchedule);
            }
        });
        helper.attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new ScheduleListAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Schedule mySchedule = adapter.getScheduleAtPosition(position);
                launchUpdateScheduleActivity(mySchedule);
            }
        });
    }

    //MakeScheduleActivityから戻ってきた値をデータベースに格納する
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            int year = data.getIntExtra("yearExt", 0);
            int month = data.getIntExtra("monthExt", 0);
            int day = data.getIntExtra("dayExt", 0);
            int hour = data.getIntExtra("hourExt", 0);
            int minute = data.getIntExtra("minuteExt", 0);
            String about = data.getStringExtra("aboutExt");
            if (requestCode == MAKE_SCHEDULE_ACTIVITY_REQUEST_CODE) {
                Schedule schedule = new Schedule(year, month, day, hour, minute, about);
                mScheduleViewModel.insert(schedule);
            } else if(requestCode == UPDATE_SCHEDULE_ACTIVITY_REQUEST_CODE) {
                int id = data.getIntExtra("idExt",-9999);
                if(id!=-9999){
                    Schedule schedule = new Schedule(id,year, month, day, hour, minute, about);
                    mScheduleViewModel.update(schedule);
                }
            }
        }
    }

    //右上にオプションメニューを作る
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    //オプションメニュー選択時の処理
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.clear_data) {
            buildDialogAlert("Confirmation","Do you want to clear all schedules?",1,null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //確認メッセージを表示
    public void buildDialogAlert(String title,String message,int commandCode,Schedule schedule){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (commandCode){
                    case 1:
                        mScheduleViewModel.deleteAll();
                        break;
                    case 2:
                        mScheduleViewModel.deleteSchedule(schedule);
                        break;
                }
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //スワイプ後削除をキャンセルすると表示からスケジュールが消えてしまう問題への対応
                if(commandCode==2){
                    recreate();
                }
            }
        });
        builder.create();
        builder.show();
    }

    public void launchUpdateScheduleActivity(Schedule schedule){
        Intent intent = new Intent(this,MakeScheduleActivity.class);
        intent.putExtra("aboutExtUpdate",schedule.getAbout());
        intent.putExtra("idExtUpdate",schedule.getId());
        startActivityForResult(intent,UPDATE_SCHEDULE_ACTIVITY_REQUEST_CODE);
    }
}