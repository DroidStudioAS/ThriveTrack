package com.aa.thrivetrack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.aa.thrivetrack.dialogs.PatchDialog;
import com.aa.thrivetrack.models.User;
import com.aa.thrivetrack.network.SessionStorage;

public class ProfileActivity extends AppCompatActivity {

    TextView usernameTv;
    TextView userRankTv;
    TextView changeUsernameTrigger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        /******Start Of Ui Initializations******/
        usernameTv=(TextView) findViewById(R.id.usernameTv);
        userRankTv = (TextView) findViewById(R.id.userRankTv);
        changeUsernameTrigger = (TextView)findViewById(R.id.changeUsernameTrigger);
        /******End Of Ui Initializations******/
        /******Start Of OnClickListeners******/
        changeUsernameTrigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PatchDialog pd = new PatchDialog(ProfileActivity.this, "username");
                pd.show();
            }
        });
        /******End Of OnClickListeners******/
        setProfilePageContent();
    }

    public void setProfilePageContent(){
        User user = SessionStorage.USER_DATA.getUser();
        usernameTv.setText(SessionStorage.getUsername());
        userRankTv.setText(user.getUser_rank());
    }

}