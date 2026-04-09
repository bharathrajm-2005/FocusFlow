package com.focusflow.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefHelper {
    private static final String PREF_NAME = "FocusApp";
    private static final String KEY_STREAK = "streak";
    private static final String KEY_POINTS = "points";
    private static final String KEY_TOTAL_SESSIONS = "totalSessions";
    private static final String KEY_TOTAL_TIME = "totalTime";

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static int getStreak(Context context) {
        return getPrefs(context).getInt(KEY_STREAK, 0);
    }

    public static void setStreak(Context context, int streak) {
        getPrefs(context).edit().putInt(KEY_STREAK, streak).apply();
    }

    public static int getPoints(Context context) {
        return getPrefs(context).getInt(KEY_POINTS, 0);
    }

    public static void setPoints(Context context, int points) {
        getPrefs(context).edit().putInt(KEY_POINTS, points).apply();
    }

    public static int getTotalSessions(Context context) {
        return getPrefs(context).getInt(KEY_TOTAL_SESSIONS, 0);
    }

    public static void setTotalSessions(Context context, int totalSessions) {
        getPrefs(context).edit().putInt(KEY_TOTAL_SESSIONS, totalSessions).apply();
    }

    public static int getTotalTime(Context context) {
        return getPrefs(context).getInt(KEY_TOTAL_TIME, 0);
    }

    public static void setTotalTime(Context context, int totalTime) {
        getPrefs(context).edit().putInt(KEY_TOTAL_TIME, totalTime).apply();
    }

    public static String getString(Context context, String key, String defaultValue) {
        return getPrefs(context).getString(key, defaultValue);
    }

    public static void setString(Context context, String key, String value) {
        getPrefs(context).edit().putString(key, value).apply();
    }

    public static int getInt(Context context, String key, int defaultValue) {
        return getPrefs(context).getInt(key, defaultValue);
    }

    public static void setInt(Context context, String key, int value) {
        getPrefs(context).edit().putInt(key, value).apply();
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        return getPrefs(context).getBoolean(key, defaultValue);
    }

    public static void setBoolean(Context context, String key, boolean value) {
        getPrefs(context).edit().putBoolean(key, value).apply();
    }
}
