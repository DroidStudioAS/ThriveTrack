package com.aa.thrivetrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
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
import com.aa.thrivetrack.fragments.setup.focus.FocusModeGoalInputFragment;
import com.aa.thrivetrack.network.SessionStorage;

public class SetupActivity extends AppCompatActivity  {

    int currentFragment = 1;
    int exploreModeInputStep = 1;

    Button nextFragmentButton;
    Fragment toGoTo = new Fragment();

    private OnFocusModeGoalInputCallback focusModeCallback;
    private OnExploreModeGoalInputCallback exploreModeCallback;
    private OnTaskInputCallback taskInputCallback;



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
                //validate input
                if(toGoTo instanceof ModePickerFragment){
                    if (SessionStorage.getModeSelected()==""){
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
                }

                //navigate to next fragment
                goToNextFragment(currentFragment);
                currentFragment++;
            }
        });
        /*****End of OnClickListeners*****/
    }
    //input is already validated if this function gets triggered
    public void goToNextFragment(int index){
        switch (index){
            case 0:
                toGoTo = new IntroductionFragment();
                break;
            case 1:
                toGoTo = new ModePickerFragment();
                break;
            case 2:
                if(SessionStorage.getModeSelected()=="focus"){
                    toGoTo = new FocusModeGoalInputFragment();
                }else if (SessionStorage.getModeSelected()=="explore"){
                    toGoTo=new ExploreModeGoalInputFragment();
                }
                break;
            case 3:
                if(SessionStorage.getModeSelected()=="focus"){
                    toGoTo = new GoalInputEndFragment();
                }else if (SessionStorage.getModeSelected()=="explore"){
                    toGoTo=new ConfirmChoiceFragment();
                }
                break;
                //todo: add select goal fragment for explore mode
            case 4:
                if(SessionStorage.getModeSelected()=="focus"){
                    toGoTo = new TaskInputExplanationFragment();
                }else if(SessionStorage.getModeSelected()=="explore"){

                }
                break;
            //todo:send explore users to taskinputexp fragment, and then taskinput
            case 5:
                if(SessionStorage.getModeSelected()=="focus"){
                    toGoTo = new TaskInputFragment();
                }else if(SessionStorage.getModeSelected()=="explore"){

                }
                break;
            case 6:
                if(SessionStorage.getModeSelected()=="focus"){
                    toGoTo = new SetupEndFragment();
                }else if(SessionStorage.getModeSelected()=="explore"){

                }
        }


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, toGoTo)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        currentFragment--;
    }
}
