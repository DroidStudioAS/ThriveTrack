package com.aa.thrivetrack.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aa.thrivetrack.R;
import com.aa.thrivetrack.callback.OnDiaryFragmentClose;

public class DiaryFragment extends Fragment {

    TextView dateTv;
    TextView entryTv;
    TextView closeButton;

    OnDiaryFragmentClose onDiaryFragmentClose;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diary, container, false);
        /**Start Of Ui Initialization**/
        dateTv=(TextView)view.findViewById(R.id.entryDateTv);
        entryTv=(TextView)view.findViewById(R.id.entryTextTv);
        closeButton=(TextView)view.findViewById(R.id.closeEntryFragmentTrigger);
        /**End Of Ui Initialization**/
        onDiaryFragmentClose=(OnDiaryFragmentClose)container.getContext();
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDiaryFragmentClose.onDiaryFragmentClose();
            }
        });
        return view;
    }
}