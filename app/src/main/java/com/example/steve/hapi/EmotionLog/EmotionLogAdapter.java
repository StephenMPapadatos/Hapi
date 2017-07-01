package com.example.steve.hapi.EmotionLog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.steve.hapi.R;
import com.example.steve.hapi.Types.Emotion;
import com.example.steve.hapi.Utilities.Emotions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.R.attr.format;
import static com.example.steve.hapi.Utilities.EmotionImages.thumbIds;

/**
 * Created by steve on 2017-05-20.
 */

public class EmotionLogAdapter<T> extends BaseAdapter {

    private List<Emotion> mEmotions;
    private Context mContext;

    public EmotionLogAdapter(@NonNull Context context, List<Emotion> emotions) {
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
            viewHolder.emotionImage = (ImageView) convertView.findViewById(R.id.imageview_emotion);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String emotion = mEmotions.get(position).getName();
        int thumbId = Emotions.valueOf(emotion.toUpperCase()).ordinal();

        viewHolder.emotion.setText(emotion);
        viewHolder.date.setText(mEmotions.get(position).getDate());
        viewHolder.emotionImage.setImageDrawable(ContextCompat.getDrawable(mContext, thumbIds[thumbId]));

        return convertView;
    }

    static class ViewHolder {
        TextView emotion;
        TextView date;

        ImageView emotionImage;
    }
}
