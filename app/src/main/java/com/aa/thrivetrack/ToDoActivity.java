package com.aa.thrivetrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Guideline;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.aa.thrivetrack.helpers.DateHelper;
import com.aa.thrivetrack.helpers.StreakHelper;
import com.aa.thrivetrack.models.Task;
import com.aa.thrivetrack.network.SessionStorage;


public class ToDoActivity extends AppCompatActivity {

    ConstraintLayout todoContainer;
    TextView todaysDateTv;
    Guideline checkboxGuideline;

    private SharedPreferences sharedPreferences;

    int checkedCount;
    boolean callApi;
    boolean todaysTasksCompleted;

    private static final String[] PATH_TO_EDIT_STREAK = new String[]{"edit","patch","user-streak"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        /**Start Of Ui Initializations**/
        todoContainer=(ConstraintLayout)findViewById(R.id.todoContainer);
        todaysDateTv=(TextView) findViewById(R.id.todaysDateTv);
        checkboxGuideline=(Guideline) findViewById(R.id.checkboxGuideline);
        /**End Of Ui Initializations**/
        todaysDateTv.setText(DateHelper.buildTodaysDate());

        sharedPreferences=getSharedPreferences(SessionStorage.getUsername(), MODE_PRIVATE);

        checkedCount=sharedPreferences.getInt("checked_count",0);
        todaysTasksCompleted=sharedPreferences.getBoolean("tasks_completed", false);

        populateUI();

    }

    public void populateUI(){
        int previousViewId = checkboxGuideline.getId();

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(todoContainer);

        for(Task task : SessionStorage.getUserData().getTasks()){
            CheckBox toAdd = new CheckBox(this);
            //generate id and typography
            setViewIdAndText(toAdd, task);
            //constraints
            setConstraints(constraintSet, toAdd, previousViewId);
            //add elements
            todoContainer.addView(toAdd);
            previousViewId=toAdd.getId();
            //todo move the onChangeListener to a seperate function
            toAdd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    callApi=false;
                    String lastCompareDate = sharedPreferences.getString("date","");
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(task.getTaskText(),isChecked);
                    editor.apply();
                    if (isChecked){
                        checkedCount++;
                    }else{
                        checkedCount--;
                    }
                    editor.putInt("checked_count", checkedCount);
                    editor.apply();
                    //Handle streak;
                    handleLocalStreakChange(checkedCount);
                    //StreakHelper.updateUserStreak(callApi, ToDoActivity.this, todaysTasksCompleted,lastCompareDate);
                }
            });

        }
        constraintSet.applyTo(todoContainer);
    }
    public void setViewIdAndText(CheckBox toAdd, Task task){
        toAdd.setId(View.generateViewId());
        toAdd.setText(task.getTaskText());
        toAdd.setTextSize(32);
        toAdd.setTextColor(Color.WHITE);
        toAdd.setChecked(sharedPreferences.getBoolean(task.getTaskText(), false));
        toAdd.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.accentgreen)));

    }
    public void setConstraints(ConstraintSet constraintSet,View toAdd, int previousViewId){
        constraintSet.connect(toAdd.getId(), ConstraintSet.TOP, previousViewId, ConstraintSet.BOTTOM, 60);
        constraintSet.connect(toAdd.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        constraintSet.connect(toAdd.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);

        constraintSet.constrainWidth(toAdd.getId(), ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainHeight(toAdd.getId(), ConstraintSet.MATCH_CONSTRAINT);
    }
    public void handleLocalStreakChange(int checkedCount){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(checkedCount==SessionStorage.getUserData().getTasks().size()){
            editor.putBoolean("tasks_completed", true);
            editor.apply();
            todaysTasksCompleted=true;
            SessionStorage.getUserData().getUser().setUser_streak(SessionStorage.getUserData().getUser().getUser_streak()+1);
            Toast.makeText(getApplicationContext(), "here it is", Toast.LENGTH_SHORT).show();
            callApi=true;
        }
        if(todaysTasksCompleted && checkedCount!=SessionStorage.getUserData().getTasks().size()){
            editor.putBoolean("tasks_completed", false);
            editor.apply();
            todaysTasksCompleted=false;
            SessionStorage.getUserData().getUser().setUser_streak(SessionStorage.getUserData().getUser().getUser_streak()-1);
            callApi=true;
        }
    }


}