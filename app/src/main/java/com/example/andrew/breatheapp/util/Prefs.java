package com.example.andrew.breatheapp.util;

import android.app.Activity;
import android.content.SharedPreferences;

import java.util.Calendar;

/**
 * Created by Andrew on 11.02.2018.
 */

public class Prefs {
    private SharedPreferences preferences;

    public Prefs(Activity activity) {
        this.preferences = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    public void setDate(long milliseconds) {
        preferences.edit().putLong("seconds", milliseconds).apply();
    }

    public String getDate() {
        long milliDate = preferences.getLong("seconds", 0);
        String amOrPm;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliDate);
        int a = calendar.get(Calendar.AM_PM);
        amOrPm = ((a == Calendar.AM) ? "AM" : "PM");


        String time = "Last " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE)
                + " " + amOrPm;
        return time;
    }

    public void setSession(int session) {
        preferences.edit().putInt("sessions", session).apply();
    }

    public int getSessions() {
        return preferences.getInt("sessions", 0);
    }

    public void setBreaths(int breaths) {
        preferences.edit().putInt("breaths", breaths).apply();//saving our breaths into system
    }

    public int getBreaths() {
        return preferences.getInt("breaths", 0);
    }
}
