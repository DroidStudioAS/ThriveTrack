package com.aa.thrivetrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.aa.thrivetrack.callback.OnChoiceConfirmed;
import com.aa.thrivetrack.callback.OnContinueClicked;
import com.aa.thrivetrack.callback.OnExploreModeGoalInputCallback;
import com.aa.thrivetrack.callback.OnFocusModeGoalInputCallback;
import com.aa.thrivetrack.callback.OnTaskInputCallback;
import com.aa.thrivetrack.fragments.setup.ModePickerFragment;
import com.aa.thrivetrack.fragments.setup.GoalInputEndFragment;
import com.aa.thrivetrack.fragments.setup.SetupEndFragment;
import com.aa.thrivetrack.fragments.setup.TaskInputExplanationFragment;
import com.aa.thrivetrack.fragments.setup.TaskInputFragment;
import com.aa.thrivetrack.fragments.setup.explore.ConfirmChoiceFragment;
import com.aa.thrivetrack.fragments.setup.explore.ExploreModeGoalInputFragment;
import com.aa.thrivetrack.fragments.setup.explore.SelectGoalFragment;
import com.aa.thrivetrack.fragments.setup.focus.FocusModeGoalInputFragment;
import com.aa.thrivetrack.helpers.DateHelper;
import com.aa.thrivetrack.models.Data;
import com.aa.thrivetrack.models.Diary;
import com.aa.thrivetrack.models.Streak;
import com.aa.thrivetrack.models.Task;
import com.aa.thrivetrack.models.User;
import com.aa.thrivetrack.network.NetworkHelper;
import com.aa.thrivetrack.network.SessionStorage;
import com.aa.thrivetrack.validation.SetupValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SetupActivity extends AppCompatActivity implements OnContinueClicked {

    /*
    1-introduction fragment
    2-mode picker
    3-focus or explore goal input
    4-focus-task input exp end explore-confirm choice
    5-focus- task input explore task input exp
    6- focus - setup end explore- task input
    7-explore setup end
     */

    int currentIndex=1;
    TextView nextFragmentButton;
    TextView exampleButton;


    Fragment next;

    private OnFocusModeGoalInputCallback focusModeCallback;
    private OnExploreModeGoalInputCallback exploreModeCallback;
    private OnTaskInputCallback taskInputCallback;
    private OnChoiceConfirmed onChoiceConfirmed;

    private static final String[] PATH_TO_WRITE = new String[]{"write","user-goal-and-tasks"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        /*****Start of Ui Initializations*****/
        nextFragmentButton=(TextView) findViewById(R.id.fragmentTransitionTrigger);
        exampleButton=(TextView)findViewById(R.id.exampleTrigger);
        /*****End of Ui Initializations*****/
        nextFragmentButton.setVisibility(View.INVISIBLE);
        exampleButton.setVisibility(View.INVISIBLE);
        next=new ModePickerFragment();

        /*****Start of OnClickListeners*****/
        nextFragmentButton.setOnClickListener(fragmentTransition());
        /*****End of OnClickListeners*****/
    }


    //method to determine what fragment is next and validate it
    public View.OnClickListener fragmentTransition(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(next instanceof SetupEndFragment){
                    registerUserGoalsAndTasks();
                    return;
                }

                setInterfaceCallback();

                if(SetupValidator.validateFragment(next)) {
                    currentIndex++;
                    next = determineNextFragment();
                    switchFragment(determineNextFragment());
                }
            }

        };
    }
    public void setInterfaceCallback(){
        if(next instanceof ExploreModeGoalInputFragment){
            exploreModeCallback.onInput();
        }
        if(next instanceof ConfirmChoiceFragment){
            onChoiceConfirmed.onChoiceConfirmed();
        }
        if(next instanceof TaskInputFragment){
            taskInputCallback.onInput();
        }
        if(next instanceof FocusModeGoalInputFragment){
            focusModeCallback.onInput();
        }
    }

    //callback to be defined HERE!
    public Fragment determineNextFragment() {
        Fragment fragment = new Fragment();
        switch (currentIndex) {
            case 1:
                fragment = new ModePickerFragment();
                break;
            case 2:
                switch (SessionStorage.getModeSelected()) {
                    case "focus":
                        fragment = new FocusModeGoalInputFragment();
                        focusModeCallback=(OnFocusModeGoalInputCallback) fragment;
                        break;
                    case "explore":
                        fragment = new ExploreModeGoalInputFragment();
                        exploreModeCallback=(OnExploreModeGoalInputCallback) fragment;
                        break;
                }
                break;
            case 3:
                switch (SessionStorage.getModeSelected()) {
                    case "focus":
                        fragment = new GoalInputEndFragment();
                        break;
                    case "explore":
                        fragment = new ConfirmChoiceFragment();
                        onChoiceConfirmed=(OnChoiceConfirmed) fragment;
                        break;
                }
                break;
            case 4:
                switch (SessionStorage.getModeSelected()) {
                    case "focus":
                        fragment = new TaskInputExplanationFragment();
                        break;
                    case "explore":
                        fragment = new SelectGoalFragment();
                        break;
                }
                break;
            case 5:
                switch (SessionStorage.getModeSelected()) {
                    case "focus":
                        fragment = new TaskInputFragment();
                        taskInputCallback=(OnTaskInputCallback) fragment;
                        break;
                    case "explore":
                        fragment = new GoalInputEndFragment();
                        break;
                }
                break;
            case 6:
                switch (SessionStorage.getModeSelected()) {
                    case "focus":
                        fragment = new SetupEndFragment();
                        break;
                    case "explore":
                        fragment = new TaskInputExplanationFragment();
                        break;
                }
                break;
            case 7:
                fragment=new TaskInputFragment();
                taskInputCallback=(OnTaskInputCallback) fragment;
                break;
            case 8:
                fragment=new SetupEndFragment();
                break;
        }
        return fragment;
    }

    public void switchFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
    }
    public void registerUserGoalsAndTasks(){
        Log.i("username", SessionStorage.getUsername());
        Map<String ,String> params = new HashMap<>();
        //build parameters
        params.put("username",  SessionStorage.getUsername());
        params.put("goal", SessionStorage.getGoalInFocus());
        params.put("taskOne", SessionStorage.getFirstTask());
        params.put("taskTwo", SessionStorage.getSecondTask());
        params.put("taskThree", SessionStorage.getThirdTask());
        params.put("taskFour", SessionStorage.getFourthTask());

        NetworkHelper.callPost(PATH_TO_WRITE, params,0);
        NetworkHelper.waitForReply();

        if(SessionStorage.getServerResponse().equals("true")){
            //build session data object neccessary for app functioning
            //when logging in, this will all be parsed automatically from the server
            User user = new User(Integer.parseInt(SessionStorage.getUser_id()), "basic");
            ArrayList<Task> tasks = new ArrayList<>();
            tasks.add(new Task(SessionStorage.getFirstTask()));
            tasks.add(new Task(SessionStorage.getSecondTask()));
            tasks.add(new Task(SessionStorage.getThirdTask()));
            tasks.add(new Task(SessionStorage.getFourthTask()));

            ArrayList<Diary> diary = new ArrayList<>();
            ArrayList<Streak> streaks = new ArrayList<>();
            streaks.add(new Streak(DateHelper.buildTodaysDate(), DateHelper.buildTodaysDate()));

            Data data = new Data(SessionStorage.getGoalInFocus(), tasks, user,diary, streaks);

            SessionStorage.setUserData(data);

            startActivity(new Intent(this, IndexActivity.class));
        }
        SessionStorage.resetServerResponse();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        currentIndex--;
        next=determineNextFragment();
        switchFragment(next);

    }
    //INTRODUCTION
    @Override
    public void onContinueClicked() {
        switchFragment(next);
        nextFragmentButton.setVisibility(View.VISIBLE);
        exampleButton.setVisibility(View.VISIBLE);
    }
}
