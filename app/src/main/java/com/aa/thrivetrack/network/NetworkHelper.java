package com.aa.thrivetrack.network;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

public class NetworkHelper {
    static final String BASE_URL = "http://339h123.f1eu.mars-hosting.com";

    OkHttpClient client = new OkHttpClient();

    public static String buildUrl(String[] routeParams){
        StringBuilder url = new StringBuilder();
        url.append(BASE_URL);
        if(routeParams.length>0) {
            for (String path : routeParams) {
                url.append("/" + path);
            }
        }
        return url.toString();
    }

    public static void callGet(String[] routeParams, Map<String,String> params){

        String path = buildUrl(routeParams);
        HttpUrl.Builder urlBuilder = HttpUrl.parse(path).newBuilder();

        for (Map.Entry<String,String> entry : params.entrySet()){
            urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
        }
        String URL = urlBuilder.build().toString();

        Log.i("url", URL);



    }
    public static void callPost(){

    }


}
