package com.aa.thrivetrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.aa.thrivetrack.adapters.AuthenticationAdapter;
import com.aa.thrivetrack.fragments.auth.LoginFragment;
import com.aa.thrivetrack.fragments.auth.RegisterFragment;
import com.google.android.material.tabs.TabLayout;

public class AuthenticationActivity extends AppCompatActivity {

    TextView loginButton;
    TextView signUpButton;

    FragmentContainerView fragmentContainerView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        /**Start Of Ui Initialization**/
        loginButton=(TextView) findViewById(R.id.loginButton);
        signUpButton=(TextView) findViewById(R.id.signUpButton);
        fragmentContainerView=(FragmentContainerView) findViewById(R.id.authFragmentContainer);
        /**End Of Ui Initialization**/
        loginButton.setOnClickListener(displayFragment(new LoginFragment()));
        signUpButton.setOnClickListener(displayFragment(new RegisterFragment()));

    }

    public View.OnClickListener displayFragment(Fragment fragment){
       return new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               fragmentContainerView.setVisibility(View.VISIBLE);
               getSupportFragmentManager().
                       beginTransaction()
                       .addToBackStack(null)
                       .replace(R.id.authFragmentContainer, fragment)
                       .commit();
               fragmentContainerView.bringToFront();

               loginButton.setOnClickListener(null);
               signUpButton.setOnClickListener(null);
           }
       };
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(fragmentContainerView.getVisibility()==View.VISIBLE){
            fragmentContainerView.setVisibility(View.GONE);
            loginButton.setOnClickListener(displayFragment(new LoginFragment()));
            signUpButton.setOnClickListener(displayFragment(new RegisterFragment()));
        }

    }
}