package com.aa.thrivetrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.aa.thrivetrack.models.Task;
import com.aa.thrivetrack.network.SessionStorage;

public class EditActivity extends AppCompatActivity {
    ConstraintLayout taskContainer;
    TextView editGoalTv;
    TextView editGoalTrigger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        /**Start Of UI Initializations**/
        taskContainer=(ConstraintLayout) findViewById(R.id.taskContainer);
        editGoalTv = (TextView) findViewById(R.id.editGoalTv);
        editGoalTrigger = (TextView) findViewById(R.id.editGoalTrigger);


        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(taskContainer);

        int previousViewId = R.id.taskContainer;
        /**End Of UI Initializations**/

        for (int i = 0; i<=SessionStorage.getUserData().getTasks().length; i++) {
            // Create a new ImageView for the task
            ImageView imageView = new ImageView(this);

            // Set the image resource for the ImageView (replace R.drawable.ic_menu with your desired drawable)
            imageView.setImageResource(R.drawable.ic_menu);

            // Generate a unique ID for the ImageView
            imageView.setId(View.generateViewId());

            // Add the ImageView to the taskContainer
            taskContainer.addView(imageView);

            // Set constraints to create a vertical chain
            constraintSet.connect(imageView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
            if(i==0) {
                constraintSet.connect(imageView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
            }else{
                constraintSet.connect(imageView.getId(), ConstraintSet.TOP, previousViewId, ConstraintSet.BOTTOM);
            }

            constraintSet.constrainWidth(imageView.getId(), 100); // Set width constraint
            constraintSet.constrainHeight(imageView.getId(), 100); // Set height constraint




            // Update previousViewId to the ID of the current ImageView
            previousViewId = imageView.getId();
        }

        // Apply constraints to the taskContainer
        constraintSet.applyTo(taskContainer);
    }


}
        //constraintSet.applyTo(taskContainer);
        /*Task x = SessionStorage.getUserData().getTasks()[0];
        ImageView toAdd = new ImageView(this);
        toAdd.setImageResource(R.drawable.ic_menu);
        toAdd.setId(View.generateViewId());
        taskContainer.addView(toAdd);

        constraintSet.connect(toAdd.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        constraintSet.connect(toAdd.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
        constraintSet.connect(toAdd.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.connect(toAdd.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);*/


