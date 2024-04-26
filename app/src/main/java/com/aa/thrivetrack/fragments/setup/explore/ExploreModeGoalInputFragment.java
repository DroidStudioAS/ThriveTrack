package com.aa.thrivetrack.fragments.setup.explore;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aa.thrivetrack.R;
import com.aa.thrivetrack.callback.OnExploreModeGoalInputCallback;

public class ExploreModeGoalInputFragment extends Fragment implements OnExploreModeGoalInputCallback {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_explore_mode_goal_input, container, false);

        return view;
    }

    @Override
    public void onInput() {
        Log.i("callback recieved","...");
    }
}