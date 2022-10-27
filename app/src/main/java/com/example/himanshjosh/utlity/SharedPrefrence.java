package com.example.himanshjosh.utlity;

import static com.example.himanshjosh.utlity.ConstantField.PREFS_NAME;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.FragmentActivity;

import com.example.himanshjosh.MainActivity;

public class SharedPrefrence {

    private static Context context;

    public SharedPrefrence(Context context) {
        this.context = context;
    }


    public boolean sharedPreferenceExist(String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        if (!prefs.contains(key)) {
            return true;
        } else {
            return false;
        }
    }





    public static void sharedPrefenceClear(FragmentActivity fragmentActivity) {
        fragmentActivity.getSharedPreferences(PREFS_NAME, 0).edit().clear().apply();
    }


    public static void sharedPrefenceClearKey(String key) {
        context.getSharedPreferences(PREFS_NAME, 0).edit().remove(key).apply();
    }


    public static void setInt(String key, int value) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getInt(String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getInt(key, 0);
    }

    public static void setStr( Context context,String key, String value) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getStr(FragmentActivity fragmentActivity, String key) {
        SharedPreferences prefs = fragmentActivity.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getString(key, "DNF");
    }



    public static void setBool(String key, boolean value) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBool(String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getBoolean(key, false);
    }
}