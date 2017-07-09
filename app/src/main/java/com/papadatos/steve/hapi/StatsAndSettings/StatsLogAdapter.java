package com.papadatos.steve.hapi.StatsAndSettings;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.papadatos.steve.hapi.R;
import com.papadatos.steve.hapi.Types.Emotion;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by steve on 2017-06-17.
 */

public class StatsLogAdapter<T> extends BaseAdapter {


    private List<List<Emotion>> mEmotions;
    private Context mContext;
    private String mRange;

    public StatsLogAdapter(@NonNull Context context, List<List<Emotion>> emotions, String range){
        mEmotions = emotions;
        mContext = context;
        mRange = range;
    }

    @Override
    public int getCount() {
        return mEmotions.size();
    }

    @Override
    public Object getItem(int position) {
        return mEmotions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.stats_log, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.range = (TextView) convertView.findViewById(R.id.range);
            viewHolder.amount = (TextView) convertView.findViewById(R.id.amount);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Calendar start  = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        DateFormat df = DateFormat.getDateInstance();
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy");
        try {
            Date date = df.parse(mEmotions.get(position).get(0).getDate());
            start.setTime(date);
            end.setTime(date);
            if(mRange.equals("week")){
                start.set(Calendar.DAY_OF_WEEK, start.getFirstDayOfWeek());
                end.set(Calendar.DAY_OF_WEEK, start.getFirstDayOfWeek());
                end.add(Calendar.WEEK_OF_YEAR, 1);
                ft = new SimpleDateFormat ("dd/MMM/yyyy");
            } else if(mRange.equals("month")){
                start.set(Calendar.DAY_OF_MONTH, 1);
                end.set(Calendar.DAY_OF_MONTH, 1);
                end.add(Calendar.MONTH, 1);
                ft = new SimpleDateFormat ("MMM/yyyy");
            } else {
                start.set(Calendar.DAY_OF_YEAR, 1);
                end.set(Calendar.DAY_OF_YEAR, 1);
                end.add(Calendar.YEAR, 1);
                ft = new SimpleDateFormat ("yyyy");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date s = start.getTime();
        Date e = end.getTime();

        viewHolder.range.setText(ft.format(s) + " - \n" + ft.format(e));
        viewHolder.amount.setText(String.valueOf(mEmotions.get(position).size()));
        return convertView;
    }

    static class ViewHolder {
        TextView range;
        TextView amount;
    }
}
