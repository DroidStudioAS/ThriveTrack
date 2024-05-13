package com.aa.thrivetrack.fragments.setup;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.aa.thrivetrack.R;
import com.aa.thrivetrack.callback.OnGoalConfirmed;
import com.aa.thrivetrack.network.SessionStorage;


public class TaskInputExplanationFragment extends Fragment implements OnGoalConfirmed {

    EditText goalTv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_task_input_explanation, container, false);
        /***Start of Ui initializations****/
        goalTv=(EditText)view.findViewById(R.id.goalTv);
        /***End of Ui initializations****/
        goalTv.setText(SessionStorage.getGoalInFocus());

        return view;
    }

    @Override
    public void onGoalConfirmed() {
        String goal = goalTv.getText().toString().trim();
        if(SessionStorage.getGoalInFocus().equals(goal) || goal.equals("")){
            return;
        }
        SessionStorage.setGoalInFocus(goal);
    }
}