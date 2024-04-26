package com.aa.thrivetrack.network;

import android.util.JsonReader;

import java.io.IOException;
import java.io.StringReader;

public class SessionStorage {
    private static String SERVER_RESPONSE = "";

    private static String MODE_SELECTED = "";
    private static String GOAL_IN_FOCUS = "";

    private static String firstExploreGoal = "";
    private static String secondExploreGoal = "";
    private static String thirdExploreGoal = "";
    private static String fourthExploreGoal = "";
    private static String fifthExploreGoal = "";

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

    public static String getFirstExploreGoal() {
        return firstExploreGoal;
    }

    public static String getSecondExploreGoal() {
        return secondExploreGoal;
    }

    public static String getThirdExploreGoal() {
        return thirdExploreGoal;
    }

    public static String getFourthExploreGoal() {
        return fourthExploreGoal;
    }

    public static String getFifthExploreGoal() {
        return fifthExploreGoal;
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

    public static void setFirstExploreGoal(String firstExploreGoal) {
        SessionStorage.firstExploreGoal = firstExploreGoal;
    }

    public static void setSecondExploreGoal(String secondExploreGoal) {
        SessionStorage.secondExploreGoal = secondExploreGoal;
    }

    public static void setThirdExploreGoal(String thirdExploreGoal) {
        SessionStorage.thirdExploreGoal = thirdExploreGoal;
    }

    public static void setFourthExploreGoal(String fourthExploreGoal) {
        SessionStorage.fourthExploreGoal = fourthExploreGoal;
    }

    public static void setFifthExploreGoal(String fifthExploreGoal) {
        SessionStorage.fifthExploreGoal = fifthExploreGoal;
    }

    public static boolean validateExploreGoal(int index){
        boolean isValidated = true;
        switch (index){
            case 1:
                if(firstExploreGoal.equals("")){
                    isValidated=false;
                }
                break;
            case 2:
                if(secondExploreGoal.equals("")){
                    isValidated=false;
                }
                break;
            case 3:
                if(thirdExploreGoal.equals("")){
                    isValidated=false;
                }
                break;
            case 4:
                if(fourthExploreGoal.equals("")){
                    isValidated=false;
                }
                break;
            case 5:
                if(fifthExploreGoal.equals("")){
                    isValidated=false;
                }
                break;
        }
        return isValidated;
    }
}
