package com.aa.thrivetrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class AuthenticationActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private AuthenticationAdapter authAdapter;
    private TabLayout tabLayout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        authAdapter=new AuthenticationAdapter(getSupportFragmentManager());

        /*******Ui Initializations Start******/
        viewPager = (ViewPager) findViewById(R.id.authViewPager);
        tabLayout = (TabLayout) findViewById(R.id.pagerTabs);
        /*******Ui Initializations End******/

        /***Set Up ViewPager****/
        viewPager.setAdapter(authAdapter);
        tabLayout.setupWithViewPager(viewPager);
        /***ViewPager Setup finished****/


    }
}