package com.example.steve.hapi.StatsAndSettings;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.steve.hapi.R;
import com.example.steve.hapi.Types.Emotion;

import java.util.List;

/**
 * Created by steve on 2017-06-17.
 */

public class StatsLogAdapter<T> extends BaseAdapter {


    private List<List<Emotion>> mEmotions;
    private Context mContext;

    public StatsLogAdapter(@NonNull Context context, List<List<Emotion>> emotions){
        mEmotions = emotions;
        mContext = context;
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

        viewHolder.range.setText(String.valueOf(position+1));
        viewHolder.amount.setText(String.valueOf(mEmotions.get(position).size()));
        return convertView;
    }

    static class ViewHolder {
        TextView range;
        TextView amount;
    }
}
