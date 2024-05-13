package com.aa.thrivetrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aa.thrivetrack.network.NetworkHelper;
import com.aa.thrivetrack.network.SessionStorage;
import com.aa.thrivetrack.validation.ToastFactory;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RecoveryActivity extends AppCompatActivity {

    String username="";

    TextView prompt;

    EditText usernameEt;

    Group qAndAGroup;
    TextView securityQuestionTv;
    EditText securityAnswerEt;

    Group newPasswordGroup;
    EditText newPassword;
    EditText confirmedPassword;

    TextView trigger;

    private static final String[] PATH_TO_RECOVERY = new String[]{"fetch","security-q-and-a"};
    private static final String[] PATH_TO_CHECK_ANSWER = new String[]{"authentication", "security-question"};
    private final String[] PATH_TO_EDIT_PASSWORD = new String[]{"edit","patch","password"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);
        /**Start Of UI Initializations**/

        prompt=(TextView) findViewById(R.id.recoveryPromptTv);

        usernameEt=(EditText) findViewById(R.id.usernameRecoveryInput);
        qAndAGroup=(Group) findViewById(R.id.qAndAGroup);
        securityQuestionTv=(TextView)findViewById(R.id.securityQuestionTv);
        securityAnswerEt=(EditText)findViewById(R.id.securityAnswerEt);
        newPasswordGroup=(Group) findViewById(R.id.newPasswordGroup);
        newPassword=(EditText)findViewById(R.id.newPass1);
        confirmedPassword=(EditText)findViewById(R.id.newPass2);

        trigger=(TextView) findViewById(R.id.recoveryTrigger);
        /**End Of UI Initializations**/

        trigger.setOnClickListener(recovery());

    }

    public View.OnClickListener recovery(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //first step- determine user
                if(usernameEt.getVisibility()==View.VISIBLE){
                    String usernameEntered = usernameEt.getText().toString().trim();
                    if(usernameEntered.equals("")){
                        ToastFactory.showToast(getApplicationContext(), "You Must Enter A Username");
                        return;
                    }
                    Map<String,String> params = new HashMap<>();
                    params.put("username", usernameEntered);
                    NetworkHelper.callGet(PATH_TO_RECOVERY, params, 3);
                    NetworkHelper.waitForReply();

                    if(SessionStorage.getServerResponse().equals("false")){
                        ToastFactory.showToast(getApplicationContext(),"This Username Does Not Exist");
                        SessionStorage.resetServerResponse();
                        return;
                    }
                    usernameEt.setVisibility(View.GONE);
                    username=usernameEntered;
                    qAndAGroup.setVisibility(View.VISIBLE);
                    securityQuestionTv.setText(SessionStorage.getSecurityQuestion().getSecurity_question());

                    SessionStorage.resetServerResponse();
                }
                else if(qAndAGroup.getVisibility()==View.VISIBLE) {
                    //second step- answer security question
                    String answer = securityAnswerEt.getText().toString().trim().toLowerCase(Locale.ROOT);
                    Map<String,String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("answer", answer);
                    NetworkHelper.callGet(PATH_TO_CHECK_ANSWER, params, 0);
                    NetworkHelper.waitForReply();
                    if(SessionStorage.getServerResponse().equals("false")){
                        ToastFactory.showToast(getApplicationContext(),"Wrong Answer");
                        SessionStorage.resetServerResponse();
                        return;
                    }
                    qAndAGroup.setVisibility(View.GONE);
                    newPasswordGroup.setVisibility(View.VISIBLE);

                    SessionStorage.resetServerResponse();
                }
                else if(newPasswordGroup.getVisibility()==View.VISIBLE){
                    //third step- reset password
                    String password = newPassword.getText().toString().trim();
                    String confirmed = confirmedPassword.getText().toString().trim();

                    if(!password.equals(confirmed)){
                        ToastFactory.showToast(getApplicationContext(), "Passwords Dont Match");
                        return;
                    }
                    if(password.equals("") || confirmed.equals("")){
                        ToastFactory.showToast(getApplicationContext(),"Please Enter Both Passwords");
                        return;
                    }

                    Map<String,String> params = new HashMap();
                    params.put("username",username);
                    params.put("password", password);

                    NetworkHelper.callPatch(PATH_TO_EDIT_PASSWORD,params,0);
                    NetworkHelper.waitForReply();
                    if(SessionStorage.getServerResponse().equals("true")){
                        ToastFactory.showToast(getApplicationContext(), "Password Updated");
                        startActivity(new Intent(getApplicationContext(), AuthenticationActivity.class));
                    }else if(SessionStorage.getServerResponse().equals("false")){
                        ToastFactory.showToast(getApplicationContext(), "Update Failed");
                    }
                    SessionStorage.resetServerResponse();

                }
            }
        };
    }
}