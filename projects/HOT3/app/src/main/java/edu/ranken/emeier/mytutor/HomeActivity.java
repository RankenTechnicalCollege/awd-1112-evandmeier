package edu.ranken.emeier.mytutor;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    // constants
    private final String TAG = "HomeActivity";

    // list of articles
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FloatingActionButton mFab;
    private ArrayList<Article> mArticleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Log after launching activity
        Log.d(TAG, "Created HomeActivity");

        // get widgets
        mToolbar = findViewById(R.id.toolbar);
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);
        mFab = findViewById(R.id.fab);

        //configure toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // configure TabLayout
        mTabLayout.addTab(mTabLayout.newTab().setText("Java"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Android"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Web Dev"));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // use PagerAdapter to manage page views in fragments
        // each page is represented by its own fragment
        mArticleList = initializeArticleList();
        Intent intent = getIntent();
        if (intent.getParcelableExtra("new_article") != null)  {
            mArticleList.add(intent.getParcelableExtra("new_article"));
        }

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), mArticleList);
        mViewPager.setAdapter(pagerAdapter);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        // configure fab
        mFab.setOnClickListener((View view) -> {
            startActivity(new Intent(this, AddArticleActivity.class));
        });
    }

    private ArrayList<Article> initializeArticleList() {
        ArrayList<Article> list = new ArrayList<>();
        list.add(new Article("Java Fella 1", "Java"));
        list.add(new Article("Java Fella 2", "Java"));
        list.add(new Article("Java Fella 3", "Java"));
        list.add(new Article("Android Fella 1", "Android"));
        list.add(new Article("Android Fella 2", "Android"));
        list.add(new Article("Android Fella 3", "Android"));
        list.add(new Article("Web Dev Fella 1", "Web Dev"));
        list.add(new Article("Web Dev Fella 2", "Web Dev"));
        list.add(new Article("Web Dev Fella 3", "Web Dev"));

        return list;
    }
}
