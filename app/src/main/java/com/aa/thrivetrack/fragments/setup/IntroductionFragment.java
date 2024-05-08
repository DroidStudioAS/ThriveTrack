package com.aa.thrivetrack.fragments.setup;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aa.thrivetrack.R;
import com.aa.thrivetrack.callback.OnContinueClicked;


public class IntroductionFragment extends Fragment {
    TextView continueButton;
    OnContinueClicked onContinueClicked;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_introduction, container, false);
        Context context= container.getContext();
        continueButton=(TextView) view.findViewById(R.id.continueButton);
        onContinueClicked=(OnContinueClicked) context;
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onContinueClicked.onContinueClicked();
            }
        });

        return view;
    }
}