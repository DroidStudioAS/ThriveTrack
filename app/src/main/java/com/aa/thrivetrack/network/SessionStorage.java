package com.aa.thrivetrack.network;

import android.util.JsonReader;

import java.io.IOException;
import java.io.StringReader;

public class SessionStorage {
    private static String SERVER_RESPONSE = "";

    private static String MODE_SELECTED = "";
    private static String GOAL_IN_FOCUS = "";


    //GETTERS
    public static String getServerResponse() {
        return SERVER_RESPONSE;
    }

    public static String getModeSelected() {
        return MODE_SELECTED;
    }

    public static String getGoalInFocus() {
        return GOAL_IN_FOCUS;
    }

    //SETTERS
    public static void setServerResponse(String serverResponse) {
        String responseToSetSplit = serverResponse.split(":")[1];
        String responseToSet = responseToSetSplit.substring(0,responseToSetSplit.length() - 1);

        SERVER_RESPONSE = responseToSet;
    }
    public static void resetServerResponse(){
        SERVER_RESPONSE="";
    }

    public static void setModeSelected(String modeSelected) {
        MODE_SELECTED = modeSelected;
    }

    public static void setGoalInFocus(String goalInFocus) {
        GOAL_IN_FOCUS = goalInFocus;
    }
}
