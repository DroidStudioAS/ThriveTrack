package com.aa.thrivetrack.fragments.setup.explore;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aa.thrivetrack.R;
import com.aa.thrivetrack.callback.OnExploreModeGoalInputCallback;
import com.aa.thrivetrack.network.SessionStorage;

public class ExploreModeGoalInputFragment extends Fragment implements OnExploreModeGoalInputCallback {

    int onGoal = 1;

    TextView goalInputLabel;
    EditText goalInput;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_explore_mode_goal_input, container, false);
        /*****Start of Ui Initializations*****/
        goalInputLabel=(TextView) view.findViewById(R.id.goalLabelExplore);
        goalInput=(EditText) view.findViewById(R.id.exploreGoalEt);
        /*****End of Ui Initializations*****/
        goalInputLabel.setText(String.valueOf(onGoal)+")");

        return view;
    }

    @Override
    public void onInput() {
        Log.i("callback", "callback");
        if(goalInput==null){
            return;
        }
         String goal = goalInput.getText().toString().trim();
        if(goal.equals("")){
           Toast.makeText(getContext(),"Please Fill The Goal In To Continue", Toast.LENGTH_SHORT).show();
           return;
        }
       setTemporaryGoals(goal);


       onGoal++;
       if(onGoal<6) {
           goalInputLabel.setText(String.valueOf(onGoal) + ")");
           goalInput.setText("");
       }



    }

    public void setTemporaryGoals(String goal){
        switch (onGoal){
            case 1:
                SessionStorage.setFirstExploreGoal(goal);
                break;
            case 2:
                SessionStorage.setSecondExploreGoal(goal);
                break;
            case 3:
                SessionStorage.setThirdExploreGoal(goal);
                break;
            case 4:
                SessionStorage.setFourthExploreGoal(goal);
                break;
            case 5:
                SessionStorage.setFifthExploreGoal(goal);
                break;
        }
    }



}