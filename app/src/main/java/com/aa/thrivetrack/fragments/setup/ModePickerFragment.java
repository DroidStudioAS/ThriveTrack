package com.aa.thrivetrack.fragments.setup;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.aa.thrivetrack.R;
import com.aa.thrivetrack.network.SessionStorage;

public class ModePickerFragment extends Fragment {
    String modeInFocus = "";

    Button focusButton;
    Button exploreButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mode_picker, container, false);
        /*****Start of Ui Initializations*****/
        focusButton = (Button)view.findViewById(R.id.focusButton);
        exploreButton = (Button)view.findViewById(R.id.exploreButton);
        /*****End of Ui Initializations*****/

        /*****Start of OnClickListeners*****/
        focusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleModeSelection(1);
                Log.i("mode in focus", modeInFocus);
            }
        });
        exploreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleModeSelection(2);
                Log.i("mode in focus", modeInFocus);
            }
        });
        /*****End of OnClickListeners*****/


        return view;
    }
    public void handleModeSelection(int index){
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
    public boolean validateInput(){
        if(modeInFocus.equals("")){
            return false;
        }
        return true;
    }
}