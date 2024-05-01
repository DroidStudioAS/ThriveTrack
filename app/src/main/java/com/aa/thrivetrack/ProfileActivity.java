package com.aa.thrivetrack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.aa.thrivetrack.network.SessionStorage;

public class ProfileActivity extends AppCompatActivity {

    TextView usernameTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        /******Start Of Ui Initializations******/
        usernameTv=(TextView) findViewById(R.id.usernameTv);
        /******End Of Ui Initializations******/


        setProfilePageContent();
    }

    public void setProfilePageContent(){
        usernameTv.setText(SessionStorage.getUsername());
    }

}