package com.example.steve.hapi.ChooseEmotion;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import static com.example.steve.hapi.Utilities.EmotionImages.thumbIds;

/**
 * Created by steve on 2017-05-14.
 */

public class EmotionAdapter extends BaseAdapter {

    private Context mContext;

    public EmotionAdapter(Context c) {
        mContext = c;
    }

    @Override
    public int getCount() {
        return thumbIds.length;
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
            imageView.setPadding(16, 16, 16, 16);
            imageView.setAdjustViewBounds(true);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageDrawable(ContextCompat.getDrawable(mContext, thumbIds[position]));
        return imageView;
    }
}
