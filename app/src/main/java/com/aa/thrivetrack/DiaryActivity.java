package com.aa.thrivetrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Guideline;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aa.thrivetrack.helpers.DateHelper;
import com.aa.thrivetrack.models.Diary;
import com.aa.thrivetrack.network.NetworkHelper;
import com.aa.thrivetrack.network.SessionStorage;

import java.util.HashMap;
import java.util.Map;

public class DiaryActivity extends AppCompatActivity {

    ConstraintLayout diaryContainer;
    Guideline diaryGuideline;

    TextView dateTv;
    EditText diaryInput;
    TextView saveTodaysInput;

    private SharedPreferences sharedPreferences;
    boolean todayEntered;

    private static final String[] PATH_TO_INSERT_ENTRY = new String[]{"write","diary"};
    private static final String[] PATH_TO_UPDATE_ENTRY = new String[]{"edit","patch","diary"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        /**Of Ui Initializations**/
        diaryContainer=(ConstraintLayout) findViewById(R.id.diaryContainer);
        diaryGuideline=(Guideline)findViewById(R.id.diaryGuideline);
        dateTv=(TextView) findViewById(R.id.dateTv);
        diaryInput=(EditText) findViewById(R.id.todaysInput);
        saveTodaysInput=(TextView) findViewById(R.id.saveTodaysDiary);
        /**Of Ui Initializations**/

        sharedPreferences=getSharedPreferences(SessionStorage.getUsername(), MODE_PRIVATE);
        String todaysInput = sharedPreferences.getString("todays_diary", "");
        todayEntered=sharedPreferences.getBoolean("diary_entered", false);
        diaryInput.setText(todaysInput);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("date", DateHelper.buildTodaysDate());

        resetSharedPreferences();
        Log.i("todayentered", String.valueOf(todayEntered));

        saveTodaysInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String entryText = diaryInput.getText().toString().trim();
                if(entryText.equals("") || entryText.equals(todaysInput)){
                    return;
                }
                Map<String,String> params = new HashMap<>();
                params.put("date", DateHelper.buildTodaysDate());
                params.put("user-id", String.valueOf(SessionStorage.getUserData().getUser().getUser_id()));
                params.put("entry-text", entryText);

                todayEntered=sharedPreferences.getBoolean("diary_entered",false);

                String[] routePath = !todayEntered ? PATH_TO_INSERT_ENTRY : PATH_TO_UPDATE_ENTRY;
                if(todayEntered){
                    NetworkHelper.callPatch(routePath, params, 0);
                }else{
                    NetworkHelper.callPost(routePath, params, 0);
                }
                NetworkHelper.waitForReply();

                if(SessionStorage.getServerResponse().equals("true")){
                    Toast.makeText(getApplicationContext(), "Diary Entry Added", Toast.LENGTH_SHORT).show();
                    editor.putBoolean("diary_entered", true);
                }

                editor.putString("todays_diary", diaryInput.getText().toString());
                editor.apply();
                SessionStorage.resetServerResponse();

            }
        });
        populateUi();

    }
    public void populateUi(){
        int previousViewId = diaryContainer.getId();
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(diaryContainer);

        Log.i("container id", String.valueOf(previousViewId));
        dateTv.setText(DateHelper.buildTodaysDate());

        for(Diary entry : SessionStorage.getUserData().getDiary()){
            if(entry.getEntry_date().equals(DateHelper.buildTodaysDate())){
                continue;
            }
            TextView dateView = new TextView(this);
            TextView diaryText = new TextView(this);
            //generate ids
            dateView.setId(View.generateViewId());
            diaryText.setId(View.generateViewId());
            //config views
            configureViews(entry,dateView,diaryText);
            //add views
            diaryContainer.addView(dateView);
            diaryContainer.addView(diaryText);
            //constraints
            setConstraints(constraintSet,dateView,diaryText,previousViewId);
            previousViewId=diaryText.getId();
            constraintSet.applyTo(diaryContainer);

        }
    }
    public void configureViews(Diary entry, TextView dateView, TextView diaryText){
        dateView.setText(entry.getEntry_date());
        diaryText.setText(entry.getEntry_text());
        dateView.setGravity(Gravity.CENTER);
        diaryText.setGravity(Gravity.CENTER);
        dateView.setTextSize(22);
        diaryText.setTextSize(16);
        dateView.setTextColor(Color.WHITE);
        diaryText.setTextColor(Color.WHITE);
    }
    public void setConstraints(ConstraintSet constraintSet, TextView dateView, TextView diaryText, int previousViewId){
        if(previousViewId==diaryContainer.getId()){
            constraintSet.connect(dateView.getId(), ConstraintSet.TOP, previousViewId,ConstraintSet.TOP);
        }else{
            constraintSet.connect(dateView.getId(), ConstraintSet.TOP, previousViewId,ConstraintSet.BOTTOM);
        }
        constraintSet.connect(dateView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID,ConstraintSet.START);
        constraintSet.connect(dateView.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID,ConstraintSet.END);

        constraintSet.constrainHeight(dateView.getId(), ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainWidth(dateView.getId(), ConstraintSet.MATCH_CONSTRAINT);

        constraintSet.connect(diaryText.getId(), ConstraintSet.TOP, dateView.getId(),ConstraintSet.BOTTOM);
        constraintSet.connect(diaryText.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID,ConstraintSet.START);
        constraintSet.connect(diaryText.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID,ConstraintSet.END);

        constraintSet.constrainHeight(diaryText.getId(), ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainWidth(diaryText.getId(), ConstraintSet.MATCH_CONSTRAINT);

    }
    public void resetSharedPreferences(){
        String lastCompareDate = sharedPreferences.getString("date","");
        if(!lastCompareDate.equals(DateHelper.buildTodaysDate())){
            //new day
            Log.i("active", "resetSharedPref");
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("todays_diary");
            editor.putBoolean("diary_entered", false);
            editor.putString("date", DateHelper.buildTodaysDate());
            editor.apply();
        }
    }
}