package com.papadatos.steve.hapi.Utilities;

import android.os.Binder;

/**
 * Created by steve on 2017-05-22.
 */

public class ObjectWrapperForBinder extends Binder {

    private final Object mData;

    public ObjectWrapperForBinder(Object data) {
        mData = data;
    }

    public Object getData() {
        return mData;
    }

}
