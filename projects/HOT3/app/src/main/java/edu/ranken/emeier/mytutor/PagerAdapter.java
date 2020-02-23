package edu.ranken.emeier.mytutor;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class PagerAdapter extends FragmentStatePagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
            case 1:
            case 2: return new TabFragment();
            default: throw new IndexOutOfBoundsException("position is invalid");
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
