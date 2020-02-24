package edu.ranken.emeier.mytutor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Article> articleList;

    public PagerAdapter(FragmentManager fm, ArrayList<Article> articles) {
        super(fm);

        articleList = articles;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        TabFragment fragment = new TabFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("articles", articleList);

        switch (position) {
            case 0:
                bundle.putString("topic", "Java");
                fragment.setArguments(bundle);
                return fragment;
            case 1:
                bundle.putString("topic", "Android");
                fragment.setArguments(bundle);
                return fragment;
            case 2:
                bundle.putString("topic", "Web Dev");
                fragment.setArguments(bundle);
                return fragment;
            default: throw new IndexOutOfBoundsException("position is invalid");
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
