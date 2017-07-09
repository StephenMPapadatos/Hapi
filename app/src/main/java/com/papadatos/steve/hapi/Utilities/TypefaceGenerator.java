package com.papadatos.steve.hapi.Utilities;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;

import com.papadatos.steve.hapi.R;

import java.util.HashMap;

/**
 * Created by steve on 2017-06-30.
 */

public class TypefaceGenerator {

    public static final String TAG = TypefaceGenerator.class.getSimpleName();

    protected static HashMap<String, Typeface> sCachedTypefaces = new HashMap<>();

    public static Typeface getCustomTypefaceFromAttributes(Context context, AttributeSet attrs) {

        Typeface tf = null;
        String font = context.getString(R.string.font_regular);

        if (attrs != null) {
            // Look up any layout-defined attributes
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomTextViewFont);
            font = a.getString(R.styleable.CustomTextViewFont_customFont);
        } else {
            Log.e(TAG, "Passed in attrs is null... will not be able to generate typeface.");
        }

        if (sCachedTypefaces.containsKey(font)) {
            tf = sCachedTypefaces.get(font);
        } else {
            try {
                tf = Typeface.createFromAsset(context.getAssets(), context.getString(R.string.font_asset_directory) + font);
                sCachedTypefaces.put(font, tf);
            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage());
            }
        }

        return tf;
    }

    public static Typeface getCustomTypefaceDirectly(Context context, String customFont) {

        Typeface tf = null;
        String font = context.getString(R.string.font_regular);

        if (customFont != null && !customFont.equals("")) {
            font = customFont;
        } else {
            Log.e(TAG, "Passed in customFont String is null... will not be able to generate typeface.");
        }

        if (sCachedTypefaces.containsKey(font)) {
            tf = sCachedTypefaces.get(font);
        } else {
            try {
                tf = Typeface.createFromAsset(context.getAssets(), context.getString(R.string.font_asset_directory) + font);
                sCachedTypefaces.put(font, tf);
            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage());
            }
        }

        return tf;
    }
}
