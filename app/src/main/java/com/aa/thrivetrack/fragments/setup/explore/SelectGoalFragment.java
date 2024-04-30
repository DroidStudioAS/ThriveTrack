package com.aa.thrivetrack.fragments.setup.explore;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aa.thrivetrack.R;
import com.aa.thrivetrack.network.SessionStorage;

public class SelectGoalFragment extends Fragment {

    TextView firstGoal;
    TextView secondGoal;
    TextView thirdGoal;
    TextView fourthGoal;
    TextView fifthGoal;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_goal, container, false);
        /*****Start of Ui Initializations*****/
        firstGoal = (TextView)view.findViewById(R.id.firstGoalButton);
        secondGoal = (TextView)view.findViewById(R.id.secondGoalButton);
        thirdGoal = (TextView)view.findViewById(R.id.thirdGoalButton);
        fourthGoal = (TextView)view.findViewById(R.id.fourthGoalButton);
        fifthGoal = (TextView)view.findViewById(R.id.fifthGoalButton);
        TextView[] goals = new TextView[]{firstGoal,secondGoal,thirdGoal,fourthGoal,fifthGoal};
        /*****End of Ui Initializations*****/
        setButtonText();
        /*****Start of OnClickListeners*****/
        for(TextView current : goals){
            current.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setAlpha(current,goals);
                }
            });
        }
        /*****End of OnClickListeners*****/
        return view;
    }
    public void setButtonText(){
        firstGoal.setText(SessionStorage.getFirstExploreGoal());
        secondGoal.setText(SessionStorage.getSecondExploreGoal());
        thirdGoal.setText(SessionStorage.getThirdExploreGoal());
        fourthGoal.setText(SessionStorage.getFourthExploreGoal());
        fifthGoal.setText(SessionStorage.getFifthExploreGoal());
    }
    public void setAlpha(TextView current, TextView[] goals){
        for (TextView x : goals){
            x.setAlpha(0.5F);
        }
        current.setAlpha(1);
    }
}