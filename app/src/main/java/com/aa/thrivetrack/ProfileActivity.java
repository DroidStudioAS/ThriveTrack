package com.aa.thrivetrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.aa.thrivetrack.callback.PatchCallback;
import com.aa.thrivetrack.dialogs.DeleteDialog;
import com.aa.thrivetrack.dialogs.DialogHelper;
import com.aa.thrivetrack.dialogs.PatchDialog;
import com.aa.thrivetrack.models.User;
import com.aa.thrivetrack.network.SessionStorage;

public class ProfileActivity extends AppCompatActivity implements PatchCallback {

    TextView usernameTv;
    TextView userRankTv;
    TextView changeUsernameTrigger;
    TextView changePasswordTrigger;
    TextView deleteTrigger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        /******Start Of Ui Initializations******/
        usernameTv=(TextView) findViewById(R.id.usernameTv);
        userRankTv = (TextView) findViewById(R.id.userRankTv);
        changeUsernameTrigger = (TextView)findViewById(R.id.changeUsernameTrigger);
        changePasswordTrigger=(TextView)findViewById(R.id.changePasswordTrigger);
        deleteTrigger=(TextView)findViewById(R.id.deleteAcountTrigger);
        /******End Of Ui Initializations******/
        /******Start Of OnClickListeners******/
        changeUsernameTrigger.setOnClickListener(DialogHelper.openPatchDialog(ProfileActivity.this, "username"));
        changePasswordTrigger.setOnClickListener(DialogHelper.openPatchDialog(ProfileActivity.this,"password"));
        deleteTrigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteDialog dd = new DeleteDialog(ProfileActivity.this);
                dd.show();
            }
        });


        /******End Of OnClickListeners******/
        setProfilePageContent();
    }

    public void setProfilePageContent(){
        User user = SessionStorage.getUserData().getUser();
        usernameTv.setText(SessionStorage.getUsername());
        userRankTv.setText(user.getUser_rank());
    }


    @Override
    public void onChange(String newValue) {
        usernameTv.setText(newValue);
    }
}