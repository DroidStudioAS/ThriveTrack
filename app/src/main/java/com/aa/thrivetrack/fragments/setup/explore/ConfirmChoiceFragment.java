package com.aa.thrivetrack.fragments.setup.explore;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.aa.thrivetrack.R;
import com.aa.thrivetrack.network.SessionStorage;

public class ConfirmChoiceFragment extends Fragment {
    EditText firstGoalEt ;
    EditText secondGoalEt;
    EditText thirdGoalEt ;
    EditText fourthGoalEt;
    EditText fifthGoalEt ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirm_choice, container, false);
        /*****Start of Ui Initializations*****/
        firstGoalEt =(EditText)view.findViewById(R.id.firstGoalEt);
        secondGoalEt=(EditText)view.findViewById(R.id.secondGoalEt);
        thirdGoalEt =(EditText)view.findViewById(R.id.thirdGoalEt);
        fourthGoalEt=(EditText)view.findViewById(R.id.fourthGoalEt);
        fifthGoalEt =(EditText)view.findViewById(R.id.fifthGoalEt);
        /*****End of Ui Initializations*****/
        displayGoals();
        return view;
    }

    public void displayGoals(){
        firstGoalEt.setText(SessionStorage.getFirstExploreGoal()) ;
        secondGoalEt.setText(SessionStorage.getSecondExploreGoal());
        thirdGoalEt.setText(SessionStorage.getThirdExploreGoal()) ;
        fourthGoalEt.setText(SessionStorage.getFourthExploreGoal());
        fifthGoalEt.setText(SessionStorage.getFifthExploreGoal());
    }
}

