package com.aa.thrivetrack.fragments.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aa.thrivetrack.R;
import com.aa.thrivetrack.SetupActivity;
import com.aa.thrivetrack.helpers.DateHelper;
import com.aa.thrivetrack.models.Streak;
import com.aa.thrivetrack.models.User;
import com.aa.thrivetrack.network.NetworkHelper;
import com.aa.thrivetrack.network.SessionStorage;
import com.aa.thrivetrack.validation.ToastFactory;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RegisterFragment extends Fragment {

    TextView registerTrigger;

    EditText usernameEt;
    EditText passwordEt;
    EditText confirmPasswordEt;
    EditText securityAwnser;

    Spinner securityQuestion;

    private static final String[] PATH_TO_REGISTER = new String[]{"authentication","register"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        /*****Start Of Ui Initializations****/
        registerTrigger = (TextView) view.findViewById(R.id.registerTrigger);
        usernameEt = (EditText)view.findViewById(R.id.registerUsernameEt);
        passwordEt = (EditText)view.findViewById(R.id.registerPassowordEt);
        confirmPasswordEt = (EditText) view.findViewById(R.id.registerConfirmPasswordEt);
        securityQuestion=(Spinner)view.findViewById(R.id.securityQuestions);
        securityAwnser=(EditText)view.findViewById(R.id.securityAwnserEt);
        /*****End Of Ui Initializations****/
        /**Spinner setup**/
        String[] securityQuestions=getResources().getStringArray(R.array.security_questions);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(), R.layout.custom_spinner, securityQuestions);
        securityQuestion.setAdapter(spinnerAdapter);

        /*****Start Of OnClickListeners****/
        registerTrigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateParams()){
                    Map<String ,String> params = new HashMap();
                    params.put("username",usernameEt.getText().toString());
                    params.put("password", passwordEt.getText().toString());
                    params.put("security-question",securityQuestion.getSelectedItem().toString());
                    params.put("security-answer", securityAwnser.getText().toString().trim().toLowerCase(Locale.ROOT));

                    params.put("streak-start", DateHelper.buildTodaysDate());
                    params.put("streak-end",DateHelper.buildTodaysDate());

                    NetworkHelper.callPost(PATH_TO_REGISTER, params,0);
                    NetworkHelper.waitForReply();
                    Log.i("response", SessionStorage.getServerResponse());
                    int userId = -1;
                    try {
                        userId = Integer.parseInt(SessionStorage.getServerResponse());
                        SessionStorage.setUser_id(String.valueOf(userId));
                    }catch (NumberFormatException e) {
                        Log.i("exc active", e.getLocalizedMessage());
                    }

                    if(!SessionStorage.getUser_id().equals(-1)){
                        SessionStorage.setUsername(usernameEt.getText().toString());
                        SessionStorage.setUser_id(String.valueOf(userId));
                        startActivity(new Intent(requireContext(), SetupActivity.class));
                    }
                    }else if(SessionStorage.getServerResponse().equals("false")){
                        ToastFactory.showToast(getContext(),"Username Taken");
                    }
                    //SessionStorage.getUserData().getStreaks().add(new Streak());
                    SessionStorage.resetServerResponse();

            }
        });
        /*****End Of OnClickListeners****/


        return view;
    }
    public boolean validateParams(){
        boolean inputValid = true;
        String username = usernameEt.getText().toString().trim();
        String password = passwordEt.getText().toString().trim();
        String confirmedPassword = confirmPasswordEt.getText().toString().trim();
        String answer = securityAwnser.getText().toString().trim();

        if(username.trim().equals("") || password.trim().equals("") || confirmedPassword.trim().equals("") || !password.equals(confirmedPassword) || answer.equals("")){
            ToastFactory.showToast(getContext(), "You Must Fill In All The Information");
            inputValid=false;
        }

        return inputValid;
    }

}