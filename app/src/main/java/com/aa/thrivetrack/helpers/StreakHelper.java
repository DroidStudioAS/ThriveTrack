package com.aa.thrivetrack.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.aa.thrivetrack.R;
import com.aa.thrivetrack.ToDoActivity;
import com.aa.thrivetrack.models.User;
import com.aa.thrivetrack.network.NetworkHelper;
import com.aa.thrivetrack.network.SessionStorage;

import java.util.HashMap;
import java.util.Map;

public class StreakHelper {
    public static final int SILVER_BORDER = 7;
    public static final int GOLD_BORDER = 30;
    public static final int PLATINUM_BORDER = 90;
    public static final int DIAMOND_BORDER = 180;

    public static final String[] PATH_TO_UPDATE_RANK = new String[]{"edit","patch","user-rank"};
    private static final String[] PATH_TO_EDIT_STREAK = new String[]{"edit","patch","user-streak"};


    public static void changeUserRank(User user, Context context){
        int userStreak = user.getUser_streak();
        String userRank = user.getUser_rank();
        String rankToSet = "";
        if (userStreak<SILVER_BORDER){
            rankToSet="bronze";
        }else if(userStreak<GOLD_BORDER){
            rankToSet="silver";
        }else if(userStreak<PLATINUM_BORDER){
            rankToSet="gold";
        }else if(userStreak<DIAMOND_BORDER){
            rankToSet="platinum";
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
            Toast.makeText(context, "your rank has been updated", Toast.LENGTH_SHORT).show();
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

    public static void updateUserStreak(boolean callApi, Context context){
        if (!callApi){
            return;
        }
        Map<String,String> params = new HashMap<>();
        params.put("user-id", String.valueOf(SessionStorage.getUserData().getUser().getUser_id()));
        params.put("streak", String.valueOf(SessionStorage.getUserData().getUser().getUser_streak()));
        params.put("end-date", DateHelper.buildTodaysDate());
        NetworkHelper.callPatch(PATH_TO_EDIT_STREAK, params, 0);
        NetworkHelper.waitForReply();
        if(SessionStorage.getServerResponse().equals("true")){
            Toast.makeText(context,"Your Streak Has Been Updated! Keep It Up", Toast.LENGTH_SHORT).show();
        }
        SessionStorage.resetServerResponse();
        StreakHelper.changeUserRank(SessionStorage.getUserData().getUser(), context);

    }
    public static void checkAndSetLastCompareDate(SharedPreferences sharedPreferences, Context context){

        boolean todaysTasksCompleted = sharedPreferences.getBoolean("tasks_completed", false);
        String lastCompareDate = sharedPreferences.getString("date","");
        if(!lastCompareDate.equals(DateHelper.buildTodaysDate())){
            //reset streak if yesterday was not completed
            if(!todaysTasksCompleted){
                SessionStorage.getUserData().getUser().setUser_streak(0);
                updateUserStreak(true, context);
                Toast.makeText(context, "Streak Reset", Toast.LENGTH_SHORT).show();
            }
            //new day
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.putString("date", DateHelper.buildTodaysDate());
            editor.apply();
        }
    }
}
