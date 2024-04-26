package com.aa.thrivetrack.fragments.setup.focus;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.aa.thrivetrack.R;
import com.aa.thrivetrack.callback.OnFocusModeGoalInputCallback;
import com.aa.thrivetrack.network.SessionStorage;

public class FocusModeGoalInputFragment extends Fragment implements OnFocusModeGoalInputCallback {

    EditText goalInput;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_focus_mode_goal_input, container, false);
        /****Start Of Ui Initializations****/
        goalInput=(EditText)view.findViewById(R.id.focusModeGoalInput);
        /****End Of Ui Initializations****/

        return view;
    }

    @Override
    public void onInput() {
        SessionStorage.setGoalInFocus(goalInput.getText().toString());
    }
}