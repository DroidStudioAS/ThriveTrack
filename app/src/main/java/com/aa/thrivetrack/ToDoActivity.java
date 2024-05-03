package com.aa.thrivetrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Guideline;

import android.os.Bundle;
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

    int checkedCount = 0;

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


        populateUI();
    }

    public void populateUI(){
        int previousViewId = checkboxGuideline.getId();

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(todoContainer);

        for(Task task : SessionStorage.getUserData().getTasks()){
            CheckBox toAdd = new CheckBox(this);
            //generate ids
            toAdd.setId(View.generateViewId());
            //typography
            toAdd.setText(task.getTaskText());
            toAdd.setTextSize(32);
            //constraints
            constraintSet.connect(toAdd.getId(), ConstraintSet.TOP, previousViewId, ConstraintSet.BOTTOM, 60);
            constraintSet.connect(toAdd.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
            constraintSet.connect(toAdd.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);

            constraintSet.constrainWidth(toAdd.getId(), ConstraintSet.WRAP_CONTENT);
            constraintSet.constrainHeight(toAdd.getId(), ConstraintSet.MATCH_CONSTRAINT);
            //add elements
            todoContainer.addView(toAdd);
            previousViewId=toAdd.getId();

            toAdd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        checkedCount++;
                    }else{
                        checkedCount--;
                    }
                    //increase streak;
                    if(checkedCount==SessionStorage.getUserData().getTasks().size()){
                        Toast.makeText(getApplicationContext(), "here it is", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        constraintSet.applyTo(todoContainer);
    }
}