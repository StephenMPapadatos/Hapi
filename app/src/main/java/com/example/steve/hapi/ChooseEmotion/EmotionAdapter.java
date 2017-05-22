package com.example.steve.hapi.ChooseEmotion;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.steve.hapi.R;

/**
 * Created by steve on 2017-05-14.
 */

public class EmotionAdapter extends BaseAdapter {

    private Context mContext;

    public static Integer[] mThumbIds = {
            R.drawable.happy, R.drawable.unhappy,
            R.drawable.angry, R.drawable.surprised,
            R.drawable.in_love, R.drawable.bored
    };

    public EmotionAdapter(Context c) {
        mContext = c;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setPadding(8, 8, 8, 8);
            imageView.setAdjustViewBounds(true);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }
}
