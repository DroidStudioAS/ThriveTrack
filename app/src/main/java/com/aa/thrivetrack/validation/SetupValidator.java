package com.aa.thrivetrack.validation;

import androidx.fragment.app.Fragment;

import com.aa.thrivetrack.fragments.setup.GoalInputEndFragment;
import com.aa.thrivetrack.fragments.setup.ModePickerFragment;
import com.aa.thrivetrack.fragments.setup.TaskInputExplanationFragment;
import com.aa.thrivetrack.fragments.setup.TaskInputFragment;
import com.aa.thrivetrack.fragments.setup.explore.ConfirmChoiceFragment;
import com.aa.thrivetrack.fragments.setup.explore.ExploreModeGoalInputFragment;
import com.aa.thrivetrack.fragments.setup.explore.SelectGoalFragment;
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
    public static boolean validateSelectedGoal(){
        return SessionStorage.getGoalInFocus().equals("") ? false : true;
    }
    public static boolean validateTasks(){
        boolean isValid = true;

        if(SessionStorage.getFirstTask().equals("") || SessionStorage.getSecondTask().equals("") || SessionStorage.getThirdTask().equals("") || SessionStorage.getFourthTask().equals("")){
            isValid=false;
        }
        return isValid;
    }
    public static boolean validateFragment(Fragment fragment){
        if(fragment instanceof ModePickerFragment){
            return validateModeSelected();
        }
        if(fragment instanceof ExploreModeGoalInputFragment || fragment instanceof ConfirmChoiceFragment){
            return validateAllGoals();
        }
        if(fragment instanceof SelectGoalFragment){
            return validateSelectedGoal();
        }
        if(fragment instanceof GoalInputEndFragment || fragment instanceof TaskInputExplanationFragment){
            return true;
        }
        if(fragment instanceof TaskInputFragment){
            return validateTasks();
        }
        return false;
    }
}
