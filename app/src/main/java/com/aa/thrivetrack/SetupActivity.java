package com.aa.thrivetrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.aa.thrivetrack.callback.OnExploreModeGoalInputCallback;
import com.aa.thrivetrack.callback.OnFocusModeGoalInputCallback;
import com.aa.thrivetrack.callback.OnTaskInputCallback;
import com.aa.thrivetrack.fragments.setup.IntroductionFragment;
import com.aa.thrivetrack.fragments.setup.ModePickerFragment;
import com.aa.thrivetrack.fragments.setup.GoalInputEndFragment;
import com.aa.thrivetrack.fragments.setup.SetupEndFragment;
import com.aa.thrivetrack.fragments.setup.TaskInputExplanationFragment;
import com.aa.thrivetrack.fragments.setup.TaskInputFragment;
import com.aa.thrivetrack.fragments.setup.explore.ConfirmChoiceFragment;
import com.aa.thrivetrack.fragments.setup.explore.ExploreModeGoalInputFragment;
import com.aa.thrivetrack.fragments.setup.explore.SelectGoalFragment;
import com.aa.thrivetrack.fragments.setup.focus.FocusModeGoalInputFragment;
import com.aa.thrivetrack.models.Data;
import com.aa.thrivetrack.models.Task;
import com.aa.thrivetrack.models.User;
import com.aa.thrivetrack.network.NetworkHelper;
import com.aa.thrivetrack.network.SessionStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SetupActivity extends AppCompatActivity  {

    int currentFragment = 1;
    int exploreModeInputStep = 1;

    Button nextFragmentButton;
    Fragment toGoTo = new Fragment();

    private OnFocusModeGoalInputCallback focusModeCallback;
    private OnExploreModeGoalInputCallback exploreModeCallback;
    private OnTaskInputCallback taskInputCallback;

    private static final String[] PATH_TO_WRITE = new String[]{"write","user-goal-and-tasks"};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        /*****Start of Ui Initializations*****/
        nextFragmentButton=(Button)findViewById(R.id.fragmentTransitionTrigger);
        /*****End of Ui Initializations*****/

        /*****Start of OnClickListeners*****/
        nextFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              validateFragmentInput();
            }
        });
        /*****End of OnClickListeners*****/
    }

    public  void validateFragmentInput(){
        //validate input
        if(toGoTo instanceof ModePickerFragment){
            if (SessionStorage.getModeSelected().equals("")){
                Toast.makeText(getApplicationContext(), "Fill Out All The Info", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        else if(toGoTo instanceof FocusModeGoalInputFragment){
            focusModeCallback= (OnFocusModeGoalInputCallback) toGoTo;
            focusModeCallback.onInput();
            if(SessionStorage.getGoalInFocus().equals("")){
                Toast.makeText(getApplicationContext(), "Fill Out All The Info", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        else if(toGoTo instanceof ExploreModeGoalInputFragment){
            exploreModeCallback= (OnExploreModeGoalInputCallback) toGoTo;
            exploreModeCallback.onInput();

            if(!SessionStorage.validateExploreGoal(exploreModeInputStep)){
                Toast.makeText(getApplicationContext(), "Fill Out All The Info", Toast.LENGTH_SHORT).show();
                return;
            }
            exploreModeInputStep++;
            if(exploreModeInputStep<=5){
                return;
            }
        }else if(toGoTo instanceof TaskInputFragment){
            taskInputCallback = (OnTaskInputCallback) toGoTo;
            taskInputCallback.onInput();
            if(!SessionStorage.validateTasks()){
                Toast.makeText(getApplicationContext(), "Fill Out All The Info", Toast.LENGTH_SHORT).show();
                return;
            }
        }else if(toGoTo instanceof SelectGoalFragment){
            if(SessionStorage.getGoalInFocus().equals("")){
                Toast.makeText(getApplicationContext(), "Please Select A Goal", Toast.LENGTH_SHORT).show();
                return;
            }
        }else if(toGoTo instanceof SetupEndFragment){
            registerUserGoalsAndTasks();
            return;
        }


        //navigate to next fragment
        goToNextFragment(currentFragment);
        currentFragment++;
    }
    public void determineFragment(int index){
        switch (index){
            case 0:
                toGoTo = new IntroductionFragment();
                break;
            case 1:
                toGoTo = new ModePickerFragment();
                break;
            case 2:
                if(SessionStorage.getModeSelected().equals("focus")){
                    toGoTo = new FocusModeGoalInputFragment();
                }else if (SessionStorage.getModeSelected().equals("explore")){
                    toGoTo=new ExploreModeGoalInputFragment();
                }
                break;
            case 3:
                if(SessionStorage.getModeSelected().equals("focus")){
                    toGoTo = new GoalInputEndFragment();
                }else if (SessionStorage.getModeSelected().equals("explore")){
                    toGoTo=new ConfirmChoiceFragment();
                }
                break;
            case 4:
                if(SessionStorage.getModeSelected().equals("focus")){
                    toGoTo = new TaskInputExplanationFragment();
                }else if(SessionStorage.getModeSelected().equals("explore")){
                    toGoTo = new SelectGoalFragment();
                }
                break;
            case 5:
                if(SessionStorage.getModeSelected().equals("focus")){
                    toGoTo = new TaskInputFragment();
                }else if(SessionStorage.getModeSelected().equals("explore")){
                    toGoTo = new TaskInputExplanationFragment();
                }
                break;
            case 6:
                if(SessionStorage.getModeSelected().equals("focus")){
                    toGoTo = new SetupEndFragment();
                }else if(SessionStorage.getModeSelected().equals("explore")){
                    toGoTo = new TaskInputFragment();
                }
                break;
            case 7:
                if(SessionStorage.getModeSelected().equals("explore")){
                    toGoTo = new SetupEndFragment();
                }
                break;

        }
    }
    //input is already validated if this function gets triggered
    public void goToNextFragment(int index){
        // Log.i("On:", String.valueOf(index));
        determineFragment(index);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, toGoTo)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
    }

    public void registerUserGoalsAndTasks(){
        Log.i("username", SessionStorage.getUsername());
        Map<String ,String> params = new HashMap();
        params.put("username",  SessionStorage.getUsername());
        params.put("goal", SessionStorage.getGoalInFocus());
        params.put("taskOne", SessionStorage.getFirstTask());
        params.put("taskTwo", SessionStorage.getSecondTask());
        params.put("taskThree", SessionStorage.getThirdTask());
        params.put("taskFour", SessionStorage.getFourthTask());

        NetworkHelper.callPost(PATH_TO_WRITE, params,0);
        NetworkHelper.waitForReply();
        Log.e("resp", SessionStorage.getServerResponse());

        if(SessionStorage.getServerResponse().equals("true")){
            //build session data object
            User user = new User(Integer.parseInt(SessionStorage.getUser_id()), "bronze");
            ArrayList<Task> tasks = new ArrayList<>();
            tasks.add(new Task(SessionStorage.getFirstTask()));
            tasks.add(new Task(SessionStorage.getSecondTask()));
            tasks.add(new Task(SessionStorage.getThirdTask()));
            tasks.add(new Task(SessionStorage.getFourthTask()));

            Data data = new Data(SessionStorage.getGoalInFocus(), tasks, user);
            SessionStorage.setUserData(data);

           startActivity(new Intent(this, IndexActivity.class));
        }


        SessionStorage.resetServerResponse();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        currentFragment--;
    }
}
