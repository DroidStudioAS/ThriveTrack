package com.aa.thrivetrack.network;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//TODO: DELETE ALL LOGS IN RELEASE VERSION
public class NetworkHelper {
    static final String BASE_URL = "http://339h123.f1eu.mars-hosting.com";

    static OkHttpClient client = new OkHttpClient();

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
    public static void waitForReply(){
        while (SessionStorage.getServerResponse().equals("")){
            Log.i("waiting","...");
        }
    }

    public static void callGet(String[] routeParams, Map<String,String> params, int executionStatus){
        //build URL
        String path = buildUrl(routeParams);
        HttpUrl.Builder urlBuilder = HttpUrl.parse(path).newBuilder();
        for (Map.Entry<String,String> entry : params.entrySet()){
            urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
        }
        String URL = urlBuilder.build().toString();
        Log.i("url", URL);
        //build request
        Request request = new Request.Builder().url(URL).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.i("failed", e.getLocalizedMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
               SessionStorage.setServerResponse(response.body().string(),executionStatus);
            }
        });



    }
    public static void callPost(String[] routeParams, Map<String,String> params, int executionStatus){
        String url = buildUrl(routeParams);
        FormBody.Builder formBuilder = new FormBody.Builder();
        for (Map.Entry<String,String> entry : params.entrySet()){
            formBuilder.add(entry.getKey(), entry.getValue());
        }
        Log.i("url", url);

        RequestBody body = formBuilder.build();
        Request postRequest = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(postRequest).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.i("Failed", e.getLocalizedMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                SessionStorage.setServerResponse(response.body().string(),executionStatus);
            }
        });

    }
}
