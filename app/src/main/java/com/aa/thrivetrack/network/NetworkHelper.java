package com.aa.thrivetrack.network;

public class NetworkHelper {
    static final String BASE_URL = "http://339h123.f1eu.mars-hosting.com/";

    public String buildUrl(String[] params){
        StringBuilder url = new StringBuilder();
        url.append(BASE_URL);
        if(params.length>0) {
            for (String path : params) {
                url.append("/" + path);
            }
        }
        return url.toString();
    }

    public static void callGet(){

    }
    public static void callPost(){

    }


}
