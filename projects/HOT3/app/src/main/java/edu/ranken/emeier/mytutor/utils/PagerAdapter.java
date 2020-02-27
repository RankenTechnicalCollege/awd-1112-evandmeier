package edu.ranken.emeier.mytutor.utils;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import edu.ranken.emeier.mytutor.TabFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private TabFragment[] mFragments;

    public PagerAdapter(FragmentManager fm, TabFragment[] fragments) {
        super(fm);

        mFragments = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return mFragments[0];
            case 1:
                return mFragments[1];
            case 2:
                return mFragments[2];
            default: throw new IndexOutOfBoundsException("position is invalid");
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}