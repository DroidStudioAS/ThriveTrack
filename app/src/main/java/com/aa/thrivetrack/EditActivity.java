package com.aa.thrivetrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aa.thrivetrack.callback.OnAddTask;
import com.aa.thrivetrack.callback.OnDeleteTask;
import com.aa.thrivetrack.callback.OnTaskChanged;
import com.aa.thrivetrack.callback.PatchCallback;
import com.aa.thrivetrack.dialogs.AddDialog;
import com.aa.thrivetrack.helpers.DialogHelper;
import com.aa.thrivetrack.dialogs.PatchDialog;
import com.aa.thrivetrack.network.SessionStorage;

public class EditActivity extends AppCompatActivity implements PatchCallback, OnTaskChanged, OnDeleteTask, OnAddTask {
    ConstraintLayout taskContainer;
    TextView editGoalTv;
    TextView editGoalTrigger;

    TextView addTaskTrigger;

    int taskTvId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        /**Start Of UI Initializations**/
        taskContainer=(ConstraintLayout) findViewById(R.id.taskContainer);
        editGoalTv = (TextView) findViewById(R.id.editGoalTv);
        editGoalTrigger = (TextView) findViewById(R.id.editGoalTrigger);
        addTaskTrigger=(TextView)findViewById(R.id.addTaskTrigger);
        /**End Of UI Initializations**/
        /**Start Of OnClickListenerss**/
        editGoalTrigger.setOnClickListener(DialogHelper.openPatchDialog(EditActivity.this,"goal"));
        addTaskTrigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDialog ad = new AddDialog(EditActivity.this, "task");
                ad.show();
            }
        });

        /**End Of OnClickListenerss**/
        editGoalTv.setText(SessionStorage.getUserData().getGoal());

        //generate tasks
        generateTasksOnUi();

    }

    public void generateTasksOnUi() {
        int previousViewId = R.id.taskContainer;
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(taskContainer);
        for (int i = 0; i < SessionStorage.getUserData().getTasks().size(); i++) {
            //elements
            ImageView imageView = new ImageView(this);
            TextView taskCounter = new TextView(this);
            TextView taskText = new TextView(this);
            int index = i;

            //set Text
            taskCounter.setText(String.valueOf(i + 1));
            taskText.setText(SessionStorage.getUserData().getTasks().get(i).getTaskText());

            if(i%2==0){
                imageView.setImageResource(R.drawable.green_circle);
            }else{
                imageView.setImageResource(R.drawable.grey_circle);
            }

            // Generate a unique ID for the UI Elements
            imageView.setId(View.generateViewId());
            taskCounter.setId(View.generateViewId());
            taskText.setId(View.generateViewId());
            //typography
            taskCounter.setGravity(Gravity.CENTER);
            taskCounter.setTextSize(30);
            taskCounter.setTextColor(getColor(R.color.background));

            taskText.setGravity(Gravity.CENTER);
            taskText.setTextSize(18);
            taskText.setTextColor(Color.BLACK);
            taskText.setBackgroundResource(R.drawable.task_background);


            // Add the ImageView to the taskContainer
            taskContainer.addView(imageView);
            taskContainer.addView(taskCounter);
            taskContainer.addView(taskText);


            int margin = i % 2 == 0 ? 32 : 100;

            // Set constraints to create a vertical chain
            constraintSet.connect(imageView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, margin);
            if (i == 0) {
                constraintSet.connect(imageView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
            } else {
                constraintSet.connect(imageView.getId(), ConstraintSet.TOP, previousViewId, ConstraintSet.BOTTOM, 40);
            }
            constraintSet.constrainWidth(imageView.getId(), 250); // Set width constraint
            constraintSet.constrainHeight(imageView.getId(), 250); // Set height constraint

            //set counter constraints
            constraintSet.connect(taskCounter.getId(), ConstraintSet.TOP, imageView.getId(), ConstraintSet.TOP);
            constraintSet.connect(taskCounter.getId(), ConstraintSet.END, imageView.getId(), ConstraintSet.END);
            constraintSet.connect(taskCounter.getId(), ConstraintSet.BOTTOM, imageView.getId(), ConstraintSet.BOTTOM);
            constraintSet.connect(taskCounter.getId(), ConstraintSet.START, imageView.getId(), ConstraintSet.START);
            //set TaskText constraints
            constraintSet.connect(taskText.getId(), ConstraintSet.TOP, imageView.getId(), ConstraintSet.TOP);
            constraintSet.connect(taskText.getId(), ConstraintSet.START, imageView.getId(), ConstraintSet.END);
            constraintSet.connect(taskText.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
            constraintSet.connect(taskText.getId(), ConstraintSet.BOTTOM, imageView.getId(), ConstraintSet.BOTTOM);

            constraintSet.constrainWidth(taskText.getId(), 600);
            // Update previousViewId to the ID of the current ImageView
            previousViewId = imageView.getId();
            //setOnClickListener For the group
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    taskTvId=taskText.getId();
                    SessionStorage.setTaskToEdit(SessionStorage.getUserData().getTasks().get(index));
                    PatchDialog pd = new PatchDialog(EditActivity.this, "task");
                    pd.show();
                }
            });

            // Apply constraints to the taskContainer
            constraintSet.applyTo(taskContainer);
        }
    }


    @Override
    public void onChange(String newValue) {
        editGoalTv.setText(newValue);
    }

    @Override
    public void onTaskChange(String task) {
        TextView toEdit = findViewById(taskTvId);
        toEdit.setText(task);
    }

    @Override
    public void onTaskDeleted() {
        recreate();
    }

    @Override
    public void onTaskAdded() {
        recreate();
    }
}


