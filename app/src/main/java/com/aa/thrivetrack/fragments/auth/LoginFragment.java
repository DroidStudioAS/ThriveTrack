package com.aa.thrivetrack.fragments.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aa.thrivetrack.IndexActivity;
import com.aa.thrivetrack.R;
import com.aa.thrivetrack.SetupActivity;
import com.aa.thrivetrack.models.Task;
import com.aa.thrivetrack.network.NetworkHelper;
import com.aa.thrivetrack.network.SessionStorage;

import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends Fragment {

    Button loginTrigger;
    EditText usernameEt;
    EditText passwordEt;

    private static final String[] PATH_TO_LOGIN = new String[]{"authentication","login"};
    private static final String[] PATH_TO_FETCH_DATA = new String[]{"fetch","user-data"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        /*****Start Of Ui Initializations****/
        loginTrigger=view.findViewById(R.id.loginTrigger);
        usernameEt = (EditText)view.findViewById(R.id.loginUsernameEt);
        passwordEt=  (EditText)view.findViewById(R.id.loginPasswordEt);
        /*****End Of Ui Initializations****/

        /*****Start Of OnClickListeners****/
        loginTrigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String ,String> params = new HashMap();

                params.put("username",usernameEt.getText().toString());
                params.put("password", passwordEt.getText().toString());

                NetworkHelper.callGet(PATH_TO_LOGIN, params,0);
                NetworkHelper.waitForReply();

                Log.i("server reponse", SessionStorage.getServerResponse());
                if(SessionStorage.getServerResponse().equals("true")){
                    startActivity(new Intent(requireContext(), SetupActivity.class));
                }else if(SessionStorage.getServerResponse().equals("false")){
                    SessionStorage.setUsername(usernameEt.getText().toString());
                    SessionStorage.resetServerResponse();
                    //fetch user data
                    Map<String,String> fetchParams = new HashMap<>();
                    fetchParams.put("username", SessionStorage.getUsername());
                    NetworkHelper.callGet(PATH_TO_FETCH_DATA, fetchParams,1);
                    NetworkHelper.waitForReply();

                    Log.i("DATA", SessionStorage.getUserData().toString());
                    startActivity(new Intent(requireContext(), IndexActivity.class));
                }
                SessionStorage.setUsername(usernameEt.getText().toString());
                SessionStorage.resetServerResponse();
                for(Task x : SessionStorage.USER_TASKS){
                    Log.i("task",x.toString());
                }

            }
        });
        /*****End Of OnClickListeners****/

        return view;
    }
}