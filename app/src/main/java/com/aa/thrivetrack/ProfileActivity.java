package com.aa.thrivetrack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.aa.thrivetrack.callback.PatchCallback;
import com.aa.thrivetrack.dialogs.PatchDialog;
import com.aa.thrivetrack.models.User;
import com.aa.thrivetrack.network.SessionStorage;

public class ProfileActivity extends AppCompatActivity implements PatchCallback {

    TextView usernameTv;
    TextView userRankTv;
    TextView changeUsernameTrigger;
    TextView changePasswordTrigger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        /******Start Of Ui Initializations******/
        usernameTv=(TextView) findViewById(R.id.usernameTv);
        userRankTv = (TextView) findViewById(R.id.userRankTv);
        changeUsernameTrigger = (TextView)findViewById(R.id.changeUsernameTrigger);
        changePasswordTrigger=(TextView)findViewById(R.id.changePasswordTrigger);
        /******End Of Ui Initializations******/
        /******Start Of OnClickListeners******/
        changeUsernameTrigger.setOnClickListener(openDialog("username"));
        changePasswordTrigger.setOnClickListener(openDialog("password"));

        /******End Of OnClickListeners******/
        setProfilePageContent();
    }

    public void setProfilePageContent(){
        User user = SessionStorage.USER_DATA.getUser();
        usernameTv.setText(SessionStorage.getUsername());
        userRankTv.setText(user.getUser_rank());
    }
    public View.OnClickListener openDialog(String mode){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PatchDialog pd = new PatchDialog(ProfileActivity.this, mode);
                pd.show();
            }
        };
    }

    @Override
    public void onUsernameChanged(String username) {
        usernameTv.setText(username);
    }
}