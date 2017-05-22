package com.example.steve.hapi.EmotionLog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.steve.hapi.R;
import com.example.steve.hapi.Types.Emotion;

import java.util.List;
/**
 * Created by steve on 2017-05-20.
 */

public class EmotionLogAdapter<T> extends BaseAdapter {

    private List<Emotion> mEmotions;
    private Context mContext;

    public EmotionLogAdapter(@NonNull Context context, List<Emotion> emotions){
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.emotion_log, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.emotion = (TextView) convertView.findViewById(R.id.emotion);
            viewHolder.date = (TextView) convertView.findViewById(R.id.date);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.emotion.setText(mEmotions.get(position).getName());
        viewHolder.date.setText(mEmotions.get(position).getDate().toString());
        return convertView;
    }

    static class ViewHolder {
        TextView emotion;
        TextView date;
    }
}
