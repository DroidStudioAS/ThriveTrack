package com.aa.thrivetrack.helpers;

import android.content.Context;
import android.widget.Toast;

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
}
