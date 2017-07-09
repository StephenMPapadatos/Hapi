package com.papadatos.steve.hapi.Controllers;

import com.papadatos.steve.hapi.Types.Emotion;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.papadatos.steve.hapi.Controllers.UserController.getEmotions;

/**
 * Created by steve on 2017-06-10.
 */

public class StatsController {

    public static List<List<Emotion>> getBest(String type, String range) throws ParseException {
        List<List<Emotion>> bestRange = new ArrayList<>();
        List<Emotion> emotions = new ArrayList<>();

        boolean isFirst = true;

        Calendar start  = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        DateFormat df = DateFormat.getDateInstance();
        Date endOfRangeStart = new Date();
        Date endOfRangeEnd = new Date();

        for(Emotion e : getEmotions(type)){
            if(isFirst){
                start.setTime(df.parse(e.getDate()));
                if(range.equals("week")){
                    start.set(Calendar.DAY_OF_WEEK, start.getFirstDayOfWeek());
                    end.set(Calendar.DAY_OF_WEEK, start.getFirstDayOfWeek());
                    end.add(Calendar.WEEK_OF_YEAR, 1);
                } else if(range.equals("month")){
                    start.set(Calendar.DAY_OF_MONTH, 1);
                    end.set(Calendar.DAY_OF_MONTH, 1);
                    end.add(Calendar.MONTH, 1);
                } else {
                    start.set(Calendar.DAY_OF_YEAR, 1);
                    end.set(Calendar.DAY_OF_YEAR, 1);
                    end.add(Calendar.YEAR, 1);
                }

                endOfRangeStart = start.getTime();
                endOfRangeEnd = end.getTime();
                isFirst = false;
            }

            Date current = df.parse(e.getDate());

            while(!(!current.before(endOfRangeStart) && current.before(endOfRangeEnd))){
                if(range.equals("week")){
                    start.add(Calendar.WEEK_OF_YEAR, -1);
                    end.add(Calendar.WEEK_OF_YEAR, -1);
                } else if(range.equals("month")){
                    start.add(Calendar.MONTH, -1);
                    end.add(Calendar.MONTH, -1);
                } else {
                    start.add(Calendar.YEAR, -1);
                    end.add(Calendar.YEAR, -1);
                }
                endOfRangeStart = start.getTime();
                endOfRangeEnd = end.getTime();
                bestRange.add(emotions);
                emotions = new ArrayList<>();
            }

            emotions.add(e);
        }

        if(emotions.size() > 0){
            bestRange.add(emotions);
        }

        return bestRange;
    }
}
