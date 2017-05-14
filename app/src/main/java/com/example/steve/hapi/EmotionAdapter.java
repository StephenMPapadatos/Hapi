package com.example.steve.hapi;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Created by steve on 2017-05-14.
 */

public class EmotionAdapter extends BaseAdapter {

    private Context mContext;

    public static Integer[] mThumbIds = {
            R.drawable.angry, R.drawable.bored,
            R.drawable.crying, R.drawable.happy,
            R.drawable.in_love, R.drawable.unhappy
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
