package com.aa.thrivetrack.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import com.aa.thrivetrack.R;
import com.aa.thrivetrack.ToDoActivity;
import com.aa.thrivetrack.models.User;
import com.aa.thrivetrack.network.NetworkHelper;
import com.aa.thrivetrack.network.SessionStorage;
import com.aa.thrivetrack.validation.ToastFactory;

import java.util.HashMap;
import java.util.Map;

public class StreakHelper {
    public static final int SILVER_BORDER = 7;
    public static final int GOLD_BORDER = 30;
    public static final int PLATINUM_BORDER = 90;
    public static final int DIAMOND_BORDER = 180;

    public static final String[] PATH_TO_UPDATE_RANK = new String[]{"edit","patch","user-rank"};
    private static final String[] PATH_TO_EDIT_STREAK = new String[]{"edit","patch","user-streak"};
    private static final String[] PATH_TO_START_NEW_STREAK = new String[]{"write","streak"};



    public static void changeUserRank(User user){
        int userStreak = user.getUser_streak();
        String userRank = user.getUser_rank();
        String rankToSet = "";
        if (userStreak<SILVER_BORDER){
            rankToSet="basic";
        }else if(userStreak<GOLD_BORDER){
            rankToSet="bronze";
        }else if(userStreak<PLATINUM_BORDER){
            rankToSet="silver";
        }else if(userStreak<DIAMOND_BORDER){
            rankToSet="gold";
        }else if(userStreak>=PLATINUM_BORDER){
            rankToSet="diamond";
        }
        if(rankToSet.equals(userRank)){
            return;
        }

        SessionStorage.getUserData().getUser().setUser_rank(rankToSet);

        Map<String, String> params = new HashMap<>();
        params.put("user-id", String.valueOf(SessionStorage.getUserData().getUser().getUser_id()));
        params.put("rank", SessionStorage.getUserData().getUser().getUser_rank());

        NetworkHelper.callPatch(PATH_TO_UPDATE_RANK, params, 0);
        NetworkHelper.waitForReply();

        if(SessionStorage.getServerResponse().equals("true")){
            Log.i("updated", "true");
        }
        SessionStorage.resetServerResponse();

    }
    public static Drawable getUserBadge(Context context){
        Drawable drawable = context.getDrawable(R.drawable.basic);
        switch (SessionStorage.getUserData().getUser().getUser_rank()){
            case "bronze":
                drawable= context.getDrawable(R.drawable.bronze);
                break;
            case "silver":
                drawable= context.getDrawable(R.drawable.silver);
                break;
            case "gold":
                drawable=context.getDrawable(R.drawable.gold);
                break;
            case "diamond":
                drawable=context.getDrawable(R.drawable.diamond);
                break;
        }
        return  drawable;
    }
    public static Drawable getUserBadge(Context context, String userRank){
        Drawable drawable = context.getDrawable(R.drawable.basic);
        switch (userRank){
            case "bronze":
                drawable= context.getDrawable(R.drawable.bronze);
                break;
            case "silver":
                drawable= context.getDrawable(R.drawable.silver);
                break;
            case "gold":
                drawable=context.getDrawable(R.drawable.gold);
                break;
            case "diamond":
                drawable=context.getDrawable(R.drawable.diamond);
                break;
        }
        return  drawable;
    }

    public static String getRankColor(String userRank){
        String colorString = "#000000";
        switch (userRank){
            case "bronze":
                colorString="#d79947";
                break;
            case "silver":
                colorString= "#e3e5ea";
                break;
            case "gold":
                colorString="#ffd700";
                break;
            case "diamond":
                colorString="#b9f2ff";
                break;
        }
        return  colorString;
    }

    public void updateUserStreak(SharedPreferences sharedPreferences){
        //get date, tasks completed and streak length;
        //get date, tasks completed and streak length;
        String todaysDate = DateHelper.buildTodaysDate();
        String lastCompareDate = sharedPreferences.getString("date", "");
        boolean tasksCompleted=sharedPreferences.getBoolean("tasks_completed", false);
        int userStreak = SessionStorage.getUserData().getUser().getUser_streak();

        //if there is no last date or user is on same day, return
        boolean isNewDay = todaysDate.equals(lastCompareDate);
        if(!isNewDay || lastCompareDate.equals("")){
            Log.i("Same day", "true");
            return;
        };
        Map<String, String> params = new HashMap<>();
        //IF new day, AND tasks are completed, AND streak>0 => EXTEND STREAK
        if(tasksCompleted && userStreak>0){
            params.put("user-id", String.valueOf(SessionStorage.getUserData().getUser().getUser_id()));
            params.put("streak", String.valueOf(SessionStorage.getUserData().getUser().getUser_streak()+1));
            params.put("end-date", lastCompareDate);
            NetworkHelper.callPatch(PATH_TO_EDIT_STREAK, params, 0);
            Log.i("extending streak", String.valueOf(true));
        }
        //IF new day, AND tasks are NOT completed, AND streak>0 =>KILL STREAK
        if(!tasksCompleted && userStreak>0){
            //just update streak to 0 and reset user rank
            SessionStorage.getUserData().getUser().setUser_streak(0);
            changeUserRank(SessionStorage.getUserData().getUser());
            Log.i("Killing streak", String.valueOf(true));
        }
        //IF new day, AND tasks are completed, AND streak===0 => START NEW STREAK
        if(tasksCompleted && userStreak==0){
            params.put("user-id", String.valueOf(SessionStorage.getUserData().getUser().getUser_id()));
            params.put("streak", String.valueOf(SessionStorage.getUserData().getUser().getUser_streak()+1));
            params.put("start-date", lastCompareDate);
            params.put("end-date", lastCompareDate);
            NetworkHelper.callPatch(PATH_TO_START_NEW_STREAK, params, 0);
            Log.i("starting streak", String.valueOf(true));

        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putString("date",todaysDate);
        editor.commit();
        Log.i("Same day", "false");


    }


}
