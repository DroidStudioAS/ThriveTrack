package com.aa.thrivetrack.network;

import android.util.JsonReader;
import android.util.Log;

import com.aa.thrivetrack.models.Task;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class SessionStorage {
    private static String username = "";

    private static String SERVER_RESPONSE = "";

    private static String MODE_SELECTED = "";
    private static String GOAL_IN_FOCUS = "";

    private static String firstExploreGoal = "";
    private static String secondExploreGoal = "";
    private static String thirdExploreGoal = "";
    private static String fourthExploreGoal = "";
    private static String fifthExploreGoal = "";

   private static String firstTask = "";
   private static String secondTask = "";
   private static String thirdTask = "";
   private static String fourthTask = "";

   public static ArrayList<Task> TASKS = new ArrayList();


    //GETTERS

    public static String getUsername() {
        return username;
    }

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

    public static String getFirstTask() {
        return firstTask;
    }

    public static String getSecondTask() {
        return secondTask;
    }

    public static String getThirdTask() {
        return thirdTask;
    }

    public static String getFourthTask() {
        return fourthTask;
    }

    //SETTERS

    public static void setUsername(String username) {
        SessionStorage.username = username;
    }

    public static void setServerResponse(String serverResponse, int executionStatus) {
        if(executionStatus==0) {
            String responseToSetSplit = serverResponse.split(":")[1];
            String responseToSet = responseToSetSplit.substring(0, responseToSetSplit.length() - 1);
            SERVER_RESPONSE = responseToSet;
        }else if(executionStatus==1){
            String [] splitArray = serverResponse.split(",\"");
            String goalResp = splitArray[0];
            String taskResp = splitArray[1];

            setGoalInFocus(goalResp.split(":")[1].split("\"")[1]);

            Gson gson = new Gson();
            Task[] tasks = gson.fromJson(taskResp.substring(7, taskResp.length()-1), Task[].class);
            for(Task x : tasks){
                TASKS.add(x);
            }
            Log.i("length", String.valueOf(tasks.length));


            SERVER_RESPONSE = serverResponse;

        }
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

    public static void setFirstTask(String firstTask) {
        SessionStorage.firstTask = firstTask;
    }

    public static void setSecondTask(String secondTask) {
        SessionStorage.secondTask = secondTask;
    }

    public static void setThirdTask(String thirdTask) {
        SessionStorage.thirdTask = thirdTask;
    }

    public static void setFourthTask(String fourthTask) {
        SessionStorage.fourthTask = fourthTask;
    }

    public static void setTasks(String first, String second, String third, String fourth){
        setFirstTask(first);
        setSecondTask(second);
        setThirdTask(third);
        setFourthTask(fourth);
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

    public static boolean validateTasks(){
        boolean isValid = true;

        if(firstTask.equals("") || secondTask.equals("") || thirdTask.equals("") || fourthTask.equals("")){
            isValid=false;
        }
        return isValid;
    }


}
