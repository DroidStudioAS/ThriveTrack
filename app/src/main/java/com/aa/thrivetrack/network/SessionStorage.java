package com.aa.thrivetrack.network;

import android.util.JsonReader;

import java.io.IOException;
import java.io.StringReader;

public class SessionStorage {
    private static String SERVER_RESPONSE = "";


    //GETTERS
    public static String getServerResponse() {
        return SERVER_RESPONSE;
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
}
