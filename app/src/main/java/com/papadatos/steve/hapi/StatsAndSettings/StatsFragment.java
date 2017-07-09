package com.papadatos.steve.hapi.StatsAndSettings;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.papadatos.steve.hapi.Controllers.UserController;
import com.papadatos.steve.hapi.R;
import com.papadatos.steve.hapi.Utilities.CustomTypefaceTextView;
import com.papadatos.steve.hapi.Utilities.NotificationScheduler;
import com.papadatos.steve.hapi.Utilities.TimePickerFragment;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StatsFragment extends Fragment {

    private static final int START_TIME = 0;
    private static final int END_TIME = 1;

    @BindView(R.id.seekbar_amount)
    protected SeekBar mSeekBarAmount;
    @BindView(R.id.button_start)
    protected CustomTypefaceTextView mButtonStart;
    @BindView(R.id.button_end)
    protected CustomTypefaceTextView mButtonEnd;
    @BindView(R.id.textview_start)
    protected TextView mTextViewStart;
    @BindView(R.id.textview_end)
    protected TextView mTextViewEnd;

    private Date mStart, mEnd;

    public StatsFragment() {
    }

    public static StatsFragment newInstance() {
        return new StatsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stats, container, false);
        ButterKnife.bind(this, view);

        int amount = UserController.getAmount();
        mStart = UserController.getStart();
        mEnd = UserController.getEnd();

        mSeekBarAmount.setProgress(amount);

        if(mStart != null){
            mTextViewStart.setText(getTime(mStart));
        }
        if(mEnd != null){
            mTextViewEnd.setText(getTime(mEnd));
        }

        return view;
    }

    public String getTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        return hours + " : " + minutes;
    }

    @OnClick(R.id.button_confirm)
    public void onClickConfirm(){
        UserController.setAmount(mSeekBarAmount.getProgress());
        setNotification();
        getFragmentManager().popBackStackImmediate();
    }

    @OnClick(R.id.button_start)
    public void clickStart() {
        TimePickerFragment newFragment = TimePickerFragment.newInstance(START_TIME);
        newFragment.setTimeSetListener(new TimePickerFragment.TimeSetListener() {
            @Override
            public void onTimeSet(int hour, int minute) {
                mTextViewStart.setText(getTime(UserController.getStart()));
            }
        });
        newFragment.show(getFragmentManager(), "timePicker");
    }

    @OnClick(R.id.button_end)
    public void clickEnd() {
        TimePickerFragment newFragment = TimePickerFragment.newInstance(END_TIME);
        newFragment.setTimeSetListener(new TimePickerFragment.TimeSetListener() {
            @Override
            public void onTimeSet(int hour, int minute) {
                mTextViewEnd.setText(getTime(UserController.getEnd()));
            }
        });
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void setNotification(){
        Date start = UserController.getStart();
        Date end = UserController.getEnd();
        int amount = UserController.getAmount();
        if (start != null && end != null && amount != 0 && (mStart != start && mEnd != end)) {
            NotificationScheduler.scheduleNotification(getContext(), "what's up?", start, end, amount);
        }
    }

}
