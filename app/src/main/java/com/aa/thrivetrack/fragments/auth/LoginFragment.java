package com.aa.thrivetrack.fragments.auth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.aa.thrivetrack.R;
import com.aa.thrivetrack.network.NetworkHelper;

import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends Fragment {

    Button loginTrigger;
    EditText usernameEt;
    EditText passwordEt;

    private static final String[] PATH_TO_LOGIN = new String[]{"authentication","login"};

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

                NetworkHelper.callGet(PATH_TO_LOGIN, params);
            }
        });
        /*****End Of OnClickListeners****/

        return view;
    }
}