package com.iwillcode.limetraytwitterassignment.Utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.iwillcode.limetraytwitterassignment.Utilities.Preferences;


public class SharedPreferencesHandler {


    private static SharedPreferences getSharedPrefs(Context context) {
        return context.getSharedPreferences(Preferences.PrefNames.PREFS_NAME_TAG, Context.MODE_PRIVATE);
    }


    public static boolean isTwitterLoggedInAlready(Context context) {
        final SharedPreferences prefs = getSharedPrefs(context);
        return prefs.getBoolean(Preferences.PrefKeys.PREF_KEY_TWITTER_LOGIN, false);
    }


    public static void setTwitterLoggedIn(Context context, boolean loggedIn) {
        SharedPreferences prefs = getSharedPrefs(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(Preferences.PrefKeys.PREF_KEY_TWITTER_LOGIN, loggedIn);
        editor.apply();
    }


    public static String getTwitterAccessToken(Context context) {
        SharedPreferences prefs = getSharedPrefs(context);
        return prefs.getString(Preferences.PrefKeys.PREF_KEY_ACCESS_TOKEN, "");
    }


    public static void setTwitterAccessToken(Context context, String accessToken) {
        SharedPreferences prefs = getSharedPrefs(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Preferences.PrefKeys.PREF_KEY_ACCESS_TOKEN, accessToken);
        editor.apply();
    }


    public static String getTwitterAccessSecret(Context context) {
        SharedPreferences prefs = getSharedPrefs(context);
        return prefs.getString(Preferences.PrefKeys.PREF_KEY_ACCESS_SECRET, "");
    }


    public static void setTwitterAccessSecret(Context context, String accessSecret) {
        SharedPreferences prefs = getSharedPrefs(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Preferences.PrefKeys.PREF_KEY_ACCESS_SECRET, accessSecret);
        editor.apply();
    }


    public static String getTwitterUsername(Context context) {
        SharedPreferences prefs = getSharedPrefs(context);
        return prefs.getString(Preferences.PrefKeys.PREF_KEY_USER_NAME, "");
    }


    public static void setTwitterUsername(Context context, String username) {
        SharedPreferences prefs = getSharedPrefs(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Preferences.PrefKeys.PREF_KEY_USER_NAME, username);
        editor.apply();
    }

    public static void clearCredentials(Context context) {
        SharedPreferences prefs = getSharedPrefs(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(Preferences.PrefKeys.PREF_KEY_ACCESS_TOKEN);
        editor.remove(Preferences.PrefKeys.PREF_KEY_ACCESS_SECRET);
        editor.remove(Preferences.PrefKeys.PREF_KEY_USER_NAME);
        editor.putBoolean(Preferences.PrefKeys.PREF_KEY_TWITTER_LOGIN, false);
        editor.commit();
    }

}