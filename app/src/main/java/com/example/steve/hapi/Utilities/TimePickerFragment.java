package com.example.steve.hapi.Utilities;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import com.example.steve.hapi.Controllers.UserController;
import com.example.steve.hapi.MainActivity;
import com.example.steve.hapi.R;

import java.util.Calendar;
import java.util.Date;


public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private static final int START_TIME = 0;
    private static final int END_TIME = 1;

    private int mId;

    private TimeSetListener mTimeSetListener;

    public static TimePickerFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt("id", id);
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        mId = getArguments().getInt("id");

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        // Do something with the time chosen by the user
        Date date;
//        Calendar c = Calendar.getInstance();

        switch (mId) {
            case START_TIME:
                date = getDate(hourOfDay, minute);
                UserController.setStart(date);
//                Date end = UserController.getEnd();
//                if(end != null){
//                    c.setTime(end);
//                    c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR));
//                    UserController.setEnd(c.getTime());
//                }
                break;
            case END_TIME:
                date = getDate(hourOfDay, minute);
                UserController.setEnd(date);
//                Date start = UserController.getStart();
//                if(start != null){
//                    c.setTime(start);
//                    c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR));
//                    UserController.setStart(c.getTime());
//                }
                break;
            default:
                break;
        }
        mTimeSetListener.onTimeSet(hourOfDay, minute);
    }

    public Date getDate(int hour, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        return c.getTime();
    }

    public interface TimeSetListener {
        void onTimeSet(int hour, int minute);
    }

    public void setTimeSetListener(TimeSetListener timeSetListener) {
        this.mTimeSetListener = timeSetListener;
    }

}
