package com.aa.thrivetrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.aa.thrivetrack.blog.BlogActivity;
import com.aa.thrivetrack.helpers.StreakHelper;
import com.aa.thrivetrack.network.NetworkHelper;
import com.aa.thrivetrack.network.SessionStorage;

import java.util.HashMap;
import java.util.Map;

public class IndexActivity extends AppCompatActivity {

    ImageView profileTrigger;
    ImageView goalsTrigger;
    ImageView todoTrigger;
    ImageView diaryTrigger;
    ImageView blogTrigger;
    ImageView halfCircle;

    //debug elements
    EditText dateInput;
    Button debugTrigger;


    private static final String[] PATH_TO_FETCH_BLOG = new String[]{"fetch","blog"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        /***Start Of Ui Initializations**/
        profileTrigger = (ImageView) findViewById(R.id.profileTrigger);
        goalsTrigger = (ImageView) findViewById(R.id.goalsTrigger);
        todoTrigger=(ImageView)findViewById(R.id.todoTrigger);
        diaryTrigger=(ImageView)findViewById(R.id.diaryTrigger);
        blogTrigger=(ImageView)findViewById(R.id.communityTrigger);
        halfCircle=(ImageView)findViewById(R.id.halfCircle);
        //debug elements
        debugTrigger=(Button) findViewById(R.id.streakDebugTrigger);
        dateInput=(EditText)findViewById(R.id.dateInput);

        /***End Of Ui Initializations**/
        /***Start Of OnClickListeners**/
        profileTrigger.setOnClickListener(pushTo(new Intent(this, ProfileActivity.class)));
        goalsTrigger.setOnClickListener(pushTo(new Intent(this, EditActivity.class)));
        todoTrigger.setOnClickListener(pushTo(new Intent(this, ToDoActivity.class)));
        diaryTrigger.setOnClickListener(pushTo(new Intent(this, DiaryActivity.class)));
        blogTrigger.setOnClickListener(pushTo(new Intent(this, BlogActivity.class)));

        debugTrigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences(SessionStorage.getUsername(), MODE_PRIVATE).edit();
                editor.putString("date", dateInput.getText().toString().trim());
                editor.commit();
            }
        });
        /***End Of OnClickListeners**/
        //fetch blog
        Map<String, String> params = new HashMap<>();
        NetworkHelper.callGet(PATH_TO_FETCH_BLOG, params, 2);
        NetworkHelper.waitForReply();
        Log.i("response",SessionStorage.getBlog().toString());
        SessionStorage.resetServerResponse();


        //initialize sp and check for streak changes
        SharedPreferences sharedPreferences = getSharedPreferences(SessionStorage.getUsername(), MODE_PRIVATE);
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