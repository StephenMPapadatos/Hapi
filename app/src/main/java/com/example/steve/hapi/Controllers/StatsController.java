package com.example.steve.hapi.Controllers;

import com.example.steve.hapi.Types.Emotion;
import com.example.steve.hapi.Types.RealmEmotion;
import com.example.steve.hapi.Types.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

import static com.example.steve.hapi.Controllers.UserController.getRealmConfiguration;

/**
 * Created by steve on 2017-06-10.
 */

public class StatsController {

    public static List<List<Emotion>> getBest(String type, String range) throws ParseException {
        List<List<Emotion>> bestRange = new ArrayList<>();
        List<Emotion> emotions = new ArrayList<>();

        boolean isFirst = true;

        Calendar cal  = Calendar.getInstance();
        DateFormat df = DateFormat.getDateInstance();
        Date endOfRange = new Date();

        for(Emotion e : getAll(type)){
            if(isFirst){
                cal.setTime(df.parse(e.getDate()));
                if(range.equals("Week")){
                    cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
                    cal.add(Calendar.WEEK_OF_YEAR, 1);
                } else if(range.equals("Month")){
                    cal.set(Calendar.DAY_OF_MONTH, 1);
                    cal.add(Calendar.MONTH, 1);
                } else {
                    cal.set(Calendar.DAY_OF_YEAR, 1);
                    cal.add(Calendar.YEAR, 1);
                }
                endOfRange = cal.getTime();
                isFirst = false;
            } else if(df.parse(e.getDate()).after(endOfRange)) {
                if(range.equals("Week")){
                    cal.add(Calendar.WEEK_OF_YEAR, 1);
                } else if(range.equals("Month")){
                    cal.add(Calendar.MONTH, 1);
                } else {
                    cal.add(Calendar.YEAR, 1);
                }
                endOfRange = cal.getTime();
                bestRange.add(emotions);
                emotions = new ArrayList<>();
            }

            emotions.add(e);
        }

        if(emotions.size() > 0){
            bestRange.add(emotions);
        }


        Collections.sort(bestRange, new Comparator<List>(){
            public int compare(List a1, List a2) {
                return a2.size() - a1.size();
            }
        });

        return bestRange;
    }

    public static List<Emotion> getAll(String type){
        Realm realm = Realm.getInstance(getRealmConfiguration());
        List<Emotion> emotions = new ArrayList<>();
        if (realm.where(User.class).findFirst() != null) {
            RealmList<RealmEmotion> em = realm.where(User.class).findFirst().getEmotions();
            RealmResults<RealmEmotion> rr = em.where().equalTo("emotion", type.toLowerCase()).findAll();
            for(RealmEmotion e: realm.copyFromRealm(rr)){
                emotions.add(new Emotion(e));
            }
        }
        realm.close();
        return emotions;
    }
}
