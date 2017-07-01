package com.example.steve.hapi.Utilities;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by steve on 2017-06-30.
 */

public class CustomTypefaceTextView extends AppCompatTextView {

    /**
     * Note that when generating the class from code, you will need
     * to call setCustomFont() manually.
     */
    public CustomTypefaceTextView(Context context) {
        super(context);
    }

    public CustomTypefaceTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(TypefaceGenerator.getCustomTypefaceFromAttributes(context, attrs));
    }

    public CustomTypefaceTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTypeface(TypefaceGenerator.getCustomTypefaceFromAttributes(context, attrs));
    }

    /**
     * Assign custom font for the view programmatically
     *
     * @param customFont The customFont (String) to assign
     */
    public void setCustomFont(Context context, String customFont) {
        setTypeface(TypefaceGenerator.getCustomTypefaceDirectly(context, customFont));
        invalidate();
        requestLayout();
    }

}