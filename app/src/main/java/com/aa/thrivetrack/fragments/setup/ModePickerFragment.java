package com.aa.thrivetrack.fragments.setup;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.aa.thrivetrack.R;
import com.aa.thrivetrack.network.SessionStorage;

public class ModePickerFragment extends Fragment {
    Context context;

    String modeInFocus = "";

    TextView focusButton;
    TextView exploreButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mode_picker, container, false);
        /*****Start of Ui Initializations*****/
        focusButton = (TextView)view.findViewById(R.id.focusButton);
        exploreButton = (TextView)view.findViewById(R.id.exploreButton);
        /*****End of Ui Initializations*****/
        context=container.getContext();

        /*****Start of OnClickListeners*****/
        focusButton.setOnClickListener(handleModeSelection(1));
        exploreButton.setOnClickListener(handleModeSelection(2));
        /*****End of OnClickListeners*****/


        return view;
    }
    public View.OnClickListener handleModeSelection(int index){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index==1){
                    modeInFocus="focus";
                    focusButton.setAlpha(1);
                    exploreButton.setAlpha(0.5F);
                }else {
                    modeInFocus = "explore";
                    focusButton.setAlpha(0.5F);
                    exploreButton.setAlpha(1);
                }
                SessionStorage.setModeSelected(modeInFocus);
            }
        };
    }
}