package com.aa.thrivetrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.aa.thrivetrack.fragments.setup.IntroductionFragment;
import com.aa.thrivetrack.fragments.setup.ModePickerFragment;

public class SetupActivity extends AppCompatActivity  {

    int currentFragment = 1;
    Button nextFragmentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        /*****Start of Ui Initializations*****/
        nextFragmentButton=(Button)findViewById(R.id.fragmentTransitionTrigger);
        /*****End of Ui Initializations*****/

        /*****Start of OnClickListeners*****/
        nextFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextFragment(currentFragment);
                currentFragment++;
            }
        });
        /*****End of OnClickListeners*****/
    }

    public void goToNextFragment(int index){
        Fragment toGoTo = new Fragment();
        switch (index){
            case 0:
                toGoTo = new IntroductionFragment();
                break;
            case 1:
                toGoTo=new ModePickerFragment();
                break;
        }


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, toGoTo)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        currentFragment--;
    }
}
