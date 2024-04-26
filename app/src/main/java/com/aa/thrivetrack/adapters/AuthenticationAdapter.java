package com.aa.thrivetrack.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.aa.thrivetrack.fragments.auth.LoginFragment;
import com.aa.thrivetrack.fragments.auth.RegisterFragment;

public class AuthenticationAdapter extends FragmentPagerAdapter {

    static final int NUM_OF_PAGES = 2;
    static final String[] TITLES = {"Login", "Register"};


    public AuthenticationAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new LoginFragment();
            case 1:
                return new RegisterFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_OF_PAGES;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }
}
