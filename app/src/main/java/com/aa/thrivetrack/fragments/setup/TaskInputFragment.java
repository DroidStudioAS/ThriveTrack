package com.aa.thrivetrack.fragments.setup;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.aa.thrivetrack.R;
import com.aa.thrivetrack.callback.OnTaskInputCallback;
import com.aa.thrivetrack.network.SessionStorage;

public class TaskInputFragment extends Fragment implements OnTaskInputCallback {
    EditText firstTaskEt;
    EditText secondTaskEt;
    EditText thirdTaskEt;
    EditText fourthTaskEt;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_input, container, false);
        /*****Start Of Ui Initializations******/
        firstTaskEt =(EditText)view.findViewById(R.id.firstTaskEt);
        secondTaskEt=(EditText)view.findViewById(R.id.secondTaskEt);
        thirdTaskEt =(EditText)view.findViewById(R.id.thirdTaskEt);
        fourthTaskEt=(EditText)view.findViewById(R.id.fourthTaskEt);
        /*****End Of Ui Initializations******/
        return view;
    }

    @Override
    public void onInput() {
        String firstTask = firstTaskEt.getText().toString().trim();
        String secondTask = secondTaskEt.getText().toString().trim();
        String thirdTask = thirdTaskEt.getText().toString().trim();
        String fourthTask = fourthTaskEt.getText().toString().trim();

        SessionStorage.setTasks(firstTask,secondTask,thirdTask,fourthTask);
    }
}