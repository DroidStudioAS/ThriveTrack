package com.aa.thrivetrack.validation;

import androidx.fragment.app.Fragment;

import com.aa.thrivetrack.fragments.setup.ModePickerFragment;
import com.aa.thrivetrack.fragments.setup.explore.ExploreModeGoalInputFragment;
import com.aa.thrivetrack.network.SessionStorage;

public class SetupValidator {
    public static boolean validateModeSelected(){
        if(SessionStorage.getModeSelected().equals("")){
            return false;
        }
        return true;
    }
    public static boolean validateAllGoals(){
        boolean isValid = true;

        if(SessionStorage.getFirstExploreGoal().equals("") || SessionStorage.getSecondExploreGoal().equals("") || SessionStorage.getThirdExploreGoal().equals("") || SessionStorage.getFourthExploreGoal().equals("") || SessionStorage.getFifthExploreGoal().equals("")){
            isValid=false;
        }
        return isValid;
    }
    public static boolean validateFragment(Fragment fragment){
        if(fragment instanceof ModePickerFragment){
            return validateModeSelected();
        }
        if(fragment instanceof ExploreModeGoalInputFragment){
            return validateAllGoals();
        }

        return false;
    }
}
