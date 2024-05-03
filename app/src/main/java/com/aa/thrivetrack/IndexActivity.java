package com.aa.thrivetrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.aa.thrivetrack.models.Data;
import com.aa.thrivetrack.models.Diary;
import com.aa.thrivetrack.network.SessionStorage;

public class IndexActivity extends AppCompatActivity {
    ImageView profileTrigger;
    ImageView goalsTrigger;
    ImageView todoTrigger;
    ImageView diaryTrigger;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        /***Start Of Ui Initializations**/
        profileTrigger = (ImageView) findViewById(R.id.profileTrigger);
        goalsTrigger = (ImageView) findViewById(R.id.goalsTrigger);
        todoTrigger=(ImageView)findViewById(R.id.todoTrigger);
        diaryTrigger=(ImageView)findViewById(R.id.diaryTrigger);
        /***End Of Ui Initializations**/
        /***Start Of OnClickListeners**/
        profileTrigger.setOnClickListener(pushTo(new Intent(this, ProfileActivity.class)));
        goalsTrigger.setOnClickListener(pushTo(new Intent(this, EditActivity.class)));
        todoTrigger.setOnClickListener(pushTo(new Intent(this, ToDoActivity.class)));
        diaryTrigger.setOnClickListener(pushTo(new Intent(this, DiaryActivity.class)));
        /***End Of OnClickListeners**/

        for(Diary x : SessionStorage.getUserData().getDiary()){
            Log.i("diary", x.toString());
        }


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