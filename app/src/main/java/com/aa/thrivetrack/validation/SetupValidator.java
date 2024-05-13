package com.aa.thrivetrack.validation;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.aa.thrivetrack.SetupActivity;
import com.aa.thrivetrack.fragments.setup.GoalInputEndFragment;
import com.aa.thrivetrack.fragments.setup.ModePickerFragment;
import com.aa.thrivetrack.fragments.setup.TaskInputExplanationFragment;
import com.aa.thrivetrack.fragments.setup.TaskInputFragment;
import com.aa.thrivetrack.fragments.setup.explore.ConfirmChoiceFragment;
import com.aa.thrivetrack.fragments.setup.explore.ExploreModeGoalInputFragment;
import com.aa.thrivetrack.fragments.setup.explore.SelectGoalFragment;
import com.aa.thrivetrack.fragments.setup.focus.FocusModeGoalInputFragment;
import com.aa.thrivetrack.network.SessionStorage;

public class SetupValidator {

    private static boolean validateModeSelected(Context context){
        if(SessionStorage.getModeSelected().equals("")){
            ToastFactory.showToast(context, "You Must Select A Mode");
            return false;
        }
        return true;
    }

    private static boolean validateAllGoals(Context context){
        boolean isValid = true;

        if(SessionStorage.getFirstExploreGoal().equals("") || SessionStorage.getSecondExploreGoal().equals("") || SessionStorage.getThirdExploreGoal().equals("") || SessionStorage.getFourthExploreGoal().equals("") || SessionStorage.getFifthExploreGoal().equals("")){
            isValid=false;
        }
        return isValid;
    }

    private static boolean validateSelectedGoal(Context context){
        boolean isValid = SessionStorage.getGoalInFocus().equals("") ? false : true;
        if(!isValid){
            ToastFactory.showToast(context, "You Must Select A Goal");
        }
        return isValid;
    }

    private static boolean validateTasks(Context context){
        boolean isValid = true;

        if(SessionStorage.getFirstTask().equals("") || SessionStorage.getSecondTask().equals("") || SessionStorage.getThirdTask().equals("") || SessionStorage.getFourthTask().equals("")){
            ToastFactory.showToast(context, "You Must Enter All 4 Tasks");
            isValid=false;
        }
        return isValid;
    }

    /*
    * This is the only method
    * accessible from the setup activity.
    * It is called whenever we are supposed to transition
    * fragments.
    * */
    public static boolean validateFragment(Fragment fragment, Context context){
        if(fragment instanceof ModePickerFragment){
            return validateModeSelected(context);
        }
        if(fragment instanceof ExploreModeGoalInputFragment || fragment instanceof ConfirmChoiceFragment){
            return validateAllGoals(context);
        }
        if(fragment instanceof SelectGoalFragment || fragment instanceof FocusModeGoalInputFragment || fragment instanceof TaskInputExplanationFragment){
            return validateSelectedGoal(context);
        }
        if(fragment instanceof GoalInputEndFragment){
            return true;
        }
        if(fragment instanceof TaskInputFragment){
            return validateTasks(context);
        }
        return false;
    }
}
