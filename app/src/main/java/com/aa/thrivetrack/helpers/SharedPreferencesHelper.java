package com.aa.thrivetrack.helpers;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.aa.thrivetrack.network.SessionStorage;

public class SharedPreferencesHelper {
    public static void spLog(Context context){
        SharedPreferences sp = context.getSharedPreferences(SessionStorage.getUsername(), MODE_PRIVATE);
        Log.i("=========SharedPreferencesLog", "=========");
        Log.i("sp date", sp.getString("date", "-1"));
        Log.i("todays tasks completed", String.valueOf(sp.getBoolean("tasks_completed", false)));
        Log.i("checked count", String.valueOf(sp.getInt("checked_count",0)));
        Log.i("=========SharedPreferencesLog", "=========");
    }
}
