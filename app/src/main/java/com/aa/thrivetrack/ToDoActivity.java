package com.aa.thrivetrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Guideline;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.aa.thrivetrack.helpers.DateHelper;
import com.aa.thrivetrack.models.Data;
import com.aa.thrivetrack.models.Task;
import com.aa.thrivetrack.network.SessionStorage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ToDoActivity extends AppCompatActivity {

    ConstraintLayout todoContainer;
    TextView todaysDateTv;
    Guideline checkboxGuideline;

    private SharedPreferences sharedPreferences;

    int checkedCount;

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

        sharedPreferences=getSharedPreferences("tasks", MODE_PRIVATE);

        checkedCount=sharedPreferences.getInt("checked_count",0);
        populateUI();
        checkAndSetLastCompareDate();
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

            toAdd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
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
                    Log.i("checked count", String.valueOf(checkedCount));
                    //increase streak;
                    if(checkedCount==SessionStorage.getUserData().getTasks().size()){
                        SessionStorage.setTodaysTasksCompleted(true);
                        SessionStorage.getUserData().getUser().setUser_streak(SessionStorage.getUserData().getUser().getUser_streak()+1);
                        Toast.makeText(getApplicationContext(), "here it is", Toast.LENGTH_SHORT).show();
                    }
                    if(SessionStorage.isTodaysTasksCompleted() && checkedCount!=SessionStorage.getUserData().getTasks().size()){
                        SessionStorage.getUserData().getUser().setUser_streak(SessionStorage.getUserData().getUser().getUser_streak()-1);
                        SessionStorage.setTodaysTasksCompleted(false);
                    }
                }
            });

        }
        constraintSet.applyTo(todoContainer);
    }
    public void checkAndSetLastCompareDate(){
        String lastCompareDate = sharedPreferences.getString("date","");
        if(!lastCompareDate.equals(DateHelper.buildTodaysDate())){
            //new day
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.putString("date", DateHelper.buildTodaysDate());
            editor.apply();
        }
    }
    public void setViewIdAndText(CheckBox toAdd, Task task){
        toAdd.setId(View.generateViewId());
        toAdd.setText(task.getTaskText());
        toAdd.setTextSize(32);
        toAdd.setChecked(sharedPreferences.getBoolean(task.getTaskText(), false));
    }
    public void setConstraints(ConstraintSet constraintSet,View toAdd, int previousViewId){
        constraintSet.connect(toAdd.getId(), ConstraintSet.TOP, previousViewId, ConstraintSet.BOTTOM, 60);
        constraintSet.connect(toAdd.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        constraintSet.connect(toAdd.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);

        constraintSet.constrainWidth(toAdd.getId(), ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainHeight(toAdd.getId(), ConstraintSet.MATCH_CONSTRAINT);
    }
}