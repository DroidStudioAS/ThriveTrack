package com.aa.thrivetrack.fragments.setup.explore;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.aa.thrivetrack.R;
import com.aa.thrivetrack.callback.OnChoiceConfirmed;
import com.aa.thrivetrack.network.SessionStorage;

public class ConfirmChoiceFragment extends Fragment implements OnChoiceConfirmed {
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

    public boolean validateGoals(){
        if(fifthGoalEt.getText().toString().equals("") || firstGoalEt.getText().toString().equals("")
                ||secondGoalEt.getText().toString().equals("") || thirdGoalEt.getText().toString().equals("")
                ||fourthGoalEt.getText().toString().equals(""))  {
            return false;
        }
        return true;
    }

    @Override
    public void onChoiceConfirmed() {
        if(fifthGoalEt==null){
            return;
        }
        if(!validateGoals()){
            Toast.makeText(getContext(),"Goals Can Not Be Blank", Toast.LENGTH_SHORT).show();
            return;
        }
        SessionStorage.setFirstExploreGoal(firstGoalEt.getText().toString());
        SessionStorage.setSecondExploreGoal(secondGoalEt.getText().toString());
        SessionStorage.setThirdExploreGoal(thirdGoalEt.getText().toString());
        SessionStorage.setFourthExploreGoal(fourthGoalEt.getText().toString());
        SessionStorage.setFifthExploreGoal(fifthGoalEt.getText().toString());

    }
}

