package com.wall.android.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.wall.android.instances.AppUser;

public class UserUtils {

    private static final String USER_KEY = "User_key";

    public static void saveUser(Context context, AppUser user) {
        SharedPreferences sharedPref = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();

        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(UserUtils.USER_KEY, json);
        editor.commit();
    }

    public static AppUser getUser(Context context) {
        SharedPreferences sharedPref = PreferenceManager
                .getDefaultSharedPreferences(context);
        String jsonUser = sharedPref.getString(UserUtils.USER_KEY, "");

        Gson gson = new Gson();
        return gson.fromJson(jsonUser, AppUser.class);
    }
}
