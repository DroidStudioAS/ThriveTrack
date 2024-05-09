package com.aa.thrivetrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Group;
import androidx.constraintlayout.widget.Guideline;
import androidx.fragment.app.FragmentContainerView;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aa.thrivetrack.blog.BlogActivity;
import com.aa.thrivetrack.callback.OnDiaryFragmentClose;
import com.aa.thrivetrack.callback.OnDiaryFragmentOpen;
import com.aa.thrivetrack.fragments.DiaryFragment;
import com.aa.thrivetrack.helpers.DateHelper;
import com.aa.thrivetrack.models.Diary;
import com.aa.thrivetrack.network.NetworkHelper;
import com.aa.thrivetrack.network.SessionStorage;

import java.util.HashMap;
import java.util.Map;

public class DiaryActivity extends AppCompatActivity implements OnDiaryFragmentClose {

    ConstraintLayout diaryContainer;
    Guideline diaryGuideline;

    TextView dateTv;
    EditText diaryInput;
    TextView saveTodaysInput;
    FragmentContainerView entryContainer;


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
        entryContainer=(FragmentContainerView)findViewById(R.id.diaryEntryContainerView);
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
            //generate ids
            dateView.setId(View.generateViewId());
            //config views
            configureViews(entry,dateView);
            //add views
            diaryContainer.addView(dateView);
            //constraints
            setConstraints(constraintSet,dateView,previousViewId);
            previousViewId=dateView.getId();
            constraintSet.applyTo(diaryContainer);
            dateView.setOnClickListener(dateClicked(entry));


        }
    }
    public void configureViews(Diary entry, TextView dateView){
        dateView.setText(entry.getEntry_date());
        dateView.setGravity(Gravity.CENTER);
        dateView.setTextSize(22);
        dateView.setTextColor(Color.parseColor("#cbcbcb"));

        dateView.setBackgroundResource(R.drawable.date_background);
        dateView.setPadding(20,10,20,10);
        dateView.setTypeface(null,Typeface.BOLD);

    }
    public void setConstraints(ConstraintSet constraintSet, TextView dateView, int previousViewId){
        if(previousViewId==diaryContainer.getId()){
            constraintSet.connect(dateView.getId(), ConstraintSet.TOP, previousViewId,ConstraintSet.TOP);
        }else{
            constraintSet.connect(dateView.getId(), ConstraintSet.TOP, previousViewId,ConstraintSet.BOTTOM, 25);
        }
        constraintSet.connect(dateView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID,ConstraintSet.START);
        constraintSet.connect(dateView.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID,ConstraintSet.END);

        constraintSet.constrainHeight(dateView.getId(), ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainWidth(dateView.getId(), ConstraintSet.WRAP_CONTENT);


    }

    public View.OnClickListener dateClicked(Diary diary){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionStorage.setDiaryInFocus(diary);

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.diaryEntryContainerView, new DiaryFragment())
                        .commit();

                Animation fadeIn = AnimationUtils.loadAnimation(DiaryActivity.this, R.anim.fade_in);
                entryContainer.setVisibility(View.VISIBLE);
                entryContainer.startAnimation(fadeIn);
                entryContainer.bringToFront();
            }
        };
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

    @Override
    public void onDiaryFragmentClose() {
            Animation fadeOut = AnimationUtils.loadAnimation(DiaryActivity.this, R.anim.fade_out);
            entryContainer.setVisibility(View.GONE);
            entryContainer.startAnimation(fadeOut);
        }
}