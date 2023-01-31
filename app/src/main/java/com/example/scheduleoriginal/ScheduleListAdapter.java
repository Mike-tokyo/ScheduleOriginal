package com.example.scheduleoriginal;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ScheduleListAdapter extends RecyclerView.Adapter<ScheduleListAdapter.ScheduleViewHolder> {
    private final LayoutInflater mInflater;
    private List<Schedule> mSchedule;
    private static ClickListener clickListener;

    ScheduleListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ScheduleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ScheduleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ScheduleViewHolder holder, int position) {
        if (mSchedule != null) {
            Schedule current = mSchedule.get(position);
            String scheText = "";
            if (current.getYear() != 0 && current.getHour() != 0) {
                if(position==0||mSchedule.get(position-1).getYear()==0){
                    scheText += "-------------------------Schedule--\n";
                }
                scheText += current.getYear() + "/" + current.getMonth() + "/" + current.getDay() + "    " + current.getHour() + ":" + current.getMinute() + "\n" + current.getAbout();
            } else if (current.getHour() != 0) {//日付のみ指定なし
                if(position==0||mSchedule.get(position-1).getHour()==0){//positionが0の時右の条件は実行されず無視されるのでエラー回避ができる
                    scheText += "-------------------------Everyday--\n";
                }
                scheText += current.getHour() + ":" + current.getMinute() + "\n" + current.getAbout();
            } else if (current.getYear() != 0) {
                if(position==0||mSchedule.get(position-1).getYear()==0){
                    scheText += "-------------------------Schedule--\n";
                }
                scheText += current.getYear() + "/" + current.getMonth() + "/" + current.getDay() + "\n" + current.getAbout();
            } else {//日付、時間指定なし
                if(position==0){
                    scheText += "--------------------------Anytime--";
                }
                scheText += "\n" + current.getAbout();
            }
            if(mSchedule.size()-1==position){//最後の要素が隠れてしまうことへの対策
                scheText += "\n";
            }
            holder.scheduleItemView.setText(scheText);
        } else {
            holder.scheduleItemView.setText("No Schedule");
        }
    }

    void setSchedule(List<Schedule> schedule) {
        mSchedule = schedule;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mSchedule != null) {
            return mSchedule.size();
        } else {
            return 0;
        }
    }

    class ScheduleViewHolder extends RecyclerView.ViewHolder {
        private final TextView scheduleItemView;

        private ScheduleViewHolder(View itemView) {
            super(itemView);
            scheduleItemView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClick(v,getAdapterPosition());
                }
            });
        }
    }

    public Schedule getScheduleAtPosition(int position) {
        return mSchedule.get(position);
    }

    public void setOnItemClickListener(ClickListener clickListener){
        ScheduleListAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v,int position);
    }
}
