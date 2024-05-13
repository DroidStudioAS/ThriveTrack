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
import android.widget.TextView;
import android.widget.Toast;

import com.aa.thrivetrack.IndexActivity;
import com.aa.thrivetrack.R;
import com.aa.thrivetrack.RecoveryActivity;
import com.aa.thrivetrack.SetupActivity;
import com.aa.thrivetrack.models.Task;
import com.aa.thrivetrack.network.NetworkHelper;
import com.aa.thrivetrack.network.SessionStorage;
import com.aa.thrivetrack.validation.ToastFactory;

import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends Fragment {

    TextView loginTrigger;
    TextView restoreTrigger;
    EditText usernameEt;
    EditText passwordEt;

    private static final String[] PATH_TO_LOGIN = new String[]{"authentication","login"};
    private static final String[] PATH_TO_FETCH_DATA = new String[]{"fetch","user-data"};
    private static final String[] PATH_TO_USER_ID = new String[]{"fetch","user-id"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        /*****Start Of Ui Initializations****/
        loginTrigger=(TextView)view.findViewById(R.id.loginTrigger);
        restoreTrigger=(TextView)view.findViewById(R.id.forgotPasswordTrigger);
        usernameEt = (EditText)view.findViewById(R.id.loginUsernameEt);
        passwordEt=  (EditText)view.findViewById(R.id.loginPasswordEt);
        /*****End Of Ui Initializations****/

        /*****Start Of OnClickListeners****/
        loginTrigger.setOnClickListener(login());
        restoreTrigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), RecoveryActivity.class));
            }
        });
        /*****End Of OnClickListeners****/

        return view;
    }

    public View.OnClickListener login(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String ,String> params = new HashMap();

                params.put("username",usernameEt.getText().toString());
                params.put("password", passwordEt.getText().toString());

                NetworkHelper.callGet(PATH_TO_LOGIN, params,0);
                NetworkHelper.waitForReply();

                if(SessionStorage.getServerResponse().equals("true")){
                    Map<String,String> fetchParams = new HashMap<>();
                    fetchParams.put("username", SessionStorage.getUsername());
                    NetworkHelper.callGet(PATH_TO_USER_ID, params, 0);
                    NetworkHelper.waitForReply();

                    SessionStorage.setUser_id(SessionStorage.getServerResponse());
                    SessionStorage.resetServerResponse();
                    startActivity(new Intent(requireContext(), SetupActivity.class));
                }else if(SessionStorage.getServerResponse().equals("false")){
                    SessionStorage.setUsername(usernameEt.getText().toString());
                    SessionStorage.resetServerResponse();
                    //fetch user data
                    Map<String,String> fetchParams = new HashMap<>();
                    fetchParams.put("username", SessionStorage.getUsername());
                    NetworkHelper.callGet(PATH_TO_FETCH_DATA, fetchParams,1);
                    NetworkHelper.waitForReply();

                    startActivity(new Intent(requireContext(), IndexActivity.class));
                }else if(SessionStorage.getServerResponse().contains("incorect")){
                    ToastFactory.showToast(getContext(), "Username/And Or Password Incorrect");
                }
                SessionStorage.setUsername(usernameEt.getText().toString());
                SessionStorage.resetServerResponse();
                for(Task x : SessionStorage.USER_TASKS){
                    Log.i("task",x.toString());
                }
            }
        };
    }
}