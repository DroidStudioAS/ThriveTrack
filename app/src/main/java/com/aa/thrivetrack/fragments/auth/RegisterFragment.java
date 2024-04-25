package com.aa.thrivetrack.fragments.auth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aa.thrivetrack.R;
import com.aa.thrivetrack.network.NetworkHelper;
import com.aa.thrivetrack.network.SessionStorage;

import java.util.HashMap;
import java.util.Map;

public class RegisterFragment extends Fragment {

    Button registerTrigger;

    EditText usernameEt;
    EditText passwordEt;
    EditText confirmPasswordEt;

    private static final String[] PATH_TO_REGISTER = new String[]{"authentication","register"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        /*****Start Of Ui Initializations****/
        registerTrigger = (Button) view.findViewById(R.id.registerTrigger);
        usernameEt = (EditText)view.findViewById(R.id.registerUsernameEt);
        passwordEt = (EditText)view.findViewById(R.id.registerPassowordEt);
        confirmPasswordEt = (EditText) view.findViewById(R.id.registerConfirmPasswordEt);
        /*****End Of Ui Initializations****/
        /*****Start Of OnClickListeners****/
        registerTrigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateParams()){
                    Map<String ,String> params = new HashMap();
                    params.put("username",usernameEt.getText().toString());
                    params.put("password", passwordEt.getText().toString());

                    NetworkHelper.callPost(PATH_TO_REGISTER, params);
                    NetworkHelper.waitForReply();

                    if(SessionStorage.getServerResponse().equals("true")){
                        Toast.makeText(getContext(), "Registered", Toast.LENGTH_SHORT).show();
                    }

                    SessionStorage.resetServerResponse();
                }
            }
        });
        /*****End Of OnClickListeners****/


        return view;
    }
    public boolean validateParams(){
        boolean inputValid = true;
        String username = usernameEt.getText().toString();
        String password = passwordEt.getText().toString();
        String confirmedPassword = confirmPasswordEt.getText().toString();

        if(username.trim().equals("") || password.trim().equals("") || confirmedPassword.trim().equals("") || !password.equals(confirmedPassword)){
            inputValid=false;
        }

        return inputValid;
    }
}