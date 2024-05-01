package com.aa.thrivetrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class IndexActivity extends AppCompatActivity {
    ImageView profileTrigger;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        /***Start Of Ui Initializations**/
        profileTrigger = (ImageView) findViewById(R.id.profileTrigger);
        /***End Of Ui Initializations**/
        /***Start Of OnClickListeners**/
        profileTrigger.setOnClickListener(pushTo(new Intent(this, ProfileActivity.class)));
        /***End Of OnClickListeners**/



    }
    public View.OnClickListener pushTo(Intent intent){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        };
    }




}