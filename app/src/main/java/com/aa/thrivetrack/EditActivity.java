package com.aa.thrivetrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Group;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.aa.thrivetrack.callback.OnDeleteTask;
import com.aa.thrivetrack.callback.OnTaskChanged;
import com.aa.thrivetrack.callback.PatchCallback;
import com.aa.thrivetrack.dialogs.DialogHelper;
import com.aa.thrivetrack.dialogs.PatchDialog;
import com.aa.thrivetrack.models.Task;
import com.aa.thrivetrack.network.SessionStorage;

public class EditActivity extends AppCompatActivity implements PatchCallback, OnTaskChanged, OnDeleteTask {
    ConstraintLayout taskContainer;
    TextView editGoalTv;
    TextView editGoalTrigger;

    int taskTvId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        /**Start Of UI Initializations**/
        taskContainer=(ConstraintLayout) findViewById(R.id.taskContainer);
        editGoalTv = (TextView) findViewById(R.id.editGoalTv);
        editGoalTrigger = (TextView) findViewById(R.id.editGoalTrigger);
        /**End Of UI Initializations**/
        /**Start Of OnClickListenerss**/
        editGoalTrigger.setOnClickListener(DialogHelper.openPatchDialog(EditActivity.this,"goal"));
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

            //set the image
            imageView.setImageResource(R.drawable.ic_menu);

            // Generate a unique ID for the UI Elements
            imageView.setId(View.generateViewId());
            taskCounter.setId(View.generateViewId());
            taskText.setId(View.generateViewId());
            //typography
            taskCounter.setGravity(Gravity.CENTER);
            taskCounter.setTextSize(18);
            taskCounter.setTextColor(getResources().getColor(R.color.text));

            taskText.setGravity(Gravity.CENTER);
            taskText.setTextSize(18);
            taskText.setTextColor(getResources().getColor(R.color.black));

            // Create a Group for ImageView and TextView
            Group group = new Group(this);
            group.setId(View.generateViewId());
            group.addView(imageView);
            group.addView(taskCounter);
            group.addView(taskText);

            // Add the ImageView to the taskContainer
            taskContainer.addView(imageView);
            taskContainer.addView(taskCounter);
            taskContainer.addView(taskText);
            taskContainer.addView(group);


            int margin = i % 2 == 0 ? 32 : 100;

            // Set constraints to create a vertical chain
            constraintSet.connect(imageView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, margin);
            if (i == 0) {
                constraintSet.connect(imageView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
            } else {
                constraintSet.connect(imageView.getId(), ConstraintSet.TOP, previousViewId, ConstraintSet.BOTTOM);
            }
            constraintSet.constrainWidth(imageView.getId(), 300); // Set width constraint
            constraintSet.constrainHeight(imageView.getId(), 300); // Set height constraint

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
}


