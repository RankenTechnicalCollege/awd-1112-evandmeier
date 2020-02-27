package edu.ranken.emeier.mytutor;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import edu.ranken.emeier.mytutor.utils.Article;
import edu.ranken.emeier.mytutor.utils.PagerAdapter;
import edu.ranken.emeier.mytutor.utils.Utilities;

public class HomeActivity extends AppCompatActivity {

    // constants
    private final String TAG = "HomeActivity";
    public static final int ADD_ARTICLE_REQUEST = 1;
    public static String JAVA_ARTICLES_EXTRA = "edu.ranken.emeier.mytutor.JavaArticlesExtra";
    public static String ANDROID_ARTICLES_EXTRA = "edu.ranken.emeier.mytutor.AndroidArticlesExtra";
    public static String WEB_ARTICLES_EXTRA = "edu.ranken.emeier.mytutor.WebArticlesExtra";
    public static String CURRENT_TAB_EXTRA = "edu.ranken.emeier.mytutor.CurrentTabExtra";

    // list of articles
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FloatingActionButton mFab;
    private ArrayList<Article> mArticleList;
    private TabFragment[] mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Log after launching activity
        Log.d(TAG, "Entered HomeActivity");

        // get widgets
        mToolbar = findViewById(R.id.toolbar);
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);
        mFab = findViewById(R.id.fab);

        //configure toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // configure TabLayout
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.topic_java));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.topic_android));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.topic_webdev));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // use PagerAdapter to manage page views in fragments
        // each page is represented by its own fragment
        mArticleList = Utilities.initializeArticleList(this);

        // initialize Java Tab Fragment
        ArrayList<Article> javaArticles = Utilities.returnListByTopic(mArticleList, getString(R.string.topic_java));
        TabFragment javaFragment = Utilities.createTabFragment(javaArticles);

        // initialize Android Tab Fragment
        ArrayList<Article> androidArticles = Utilities.returnListByTopic(mArticleList, getString(R.string.topic_android));
        TabFragment androidFragment = Utilities.createTabFragment(androidArticles);

        // initialize WebDev Tab Fragment
        ArrayList<Article> webArticles = Utilities.returnListByTopic(mArticleList, getString(R.string.topic_webdev));
        TabFragment webFragment = Utilities.createTabFragment(webArticles);

        // initialize array of fragments
        mFragments = new TabFragment[3];
        mFragments[0] = javaFragment;
        mFragments[1] = androidFragment;
        mFragments[2] = webFragment;

        // configure PagerAdapter
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), mFragments);
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
            startActivityForResult(new Intent(this, AddArticleActivity.class), ADD_ARTICLE_REQUEST);
        });

        Log.d(TAG, "HomeActivity successfully created.");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // save the current lists in each tab
        ArrayList<Article> javaList = mFragments[0].getFragmentList();
        ArrayList<Article> androidList = mFragments[1].getFragmentList();
        ArrayList<Article> webList = mFragments[2].getFragmentList();

        outState.putParcelableArrayList(JAVA_ARTICLES_EXTRA, javaList);
        outState.putParcelableArrayList(ANDROID_ARTICLES_EXTRA, androidList);
        outState.putParcelableArrayList(WEB_ARTICLES_EXTRA, webList);
        outState.putInt(CURRENT_TAB_EXTRA, mViewPager.getCurrentItem());

        Log.d(TAG, "HomeActivity instance saved.");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        ArrayList<Article> javaList = savedInstanceState.getParcelableArrayList(JAVA_ARTICLES_EXTRA);
        TabFragment javaFragment = Utilities.createTabFragment(javaList);

        ArrayList<Article> androidList = savedInstanceState.getParcelableArrayList(ANDROID_ARTICLES_EXTRA);
        TabFragment androidFragment = Utilities.createTabFragment(androidList);

        ArrayList<Article> webList = savedInstanceState.getParcelableArrayList(WEB_ARTICLES_EXTRA);
        TabFragment webFragment = Utilities.createTabFragment(webList);

        int currentTab = savedInstanceState.getInt(CURRENT_TAB_EXTRA);

        mFragments = new TabFragment[3];
        mFragments[0] = javaFragment;
        mFragments[1] = androidFragment;
        mFragments[2] = webFragment;

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(currentTab);

        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        Log.d(TAG, "HomeActivity instance restored.");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_ARTICLE_REQUEST) {
            if (resultCode == RESULT_OK) {
                Article article = data.getParcelableExtra(AddArticleActivity.NEW_ARTICLE_EXTRA);
                Log.d(TAG, "Retrieved new article from previous activity.");

                if (article.getTopic().equalsIgnoreCase(getString(R.string.topic_java))) {
                    mViewPager.setCurrentItem(0);
                    Utilities.addArticleToFragment(mFragments[0], article);
                    Log.d(TAG, "Added article to Java List.");
                } else if (article.getTopic().equalsIgnoreCase(getString(R.string.topic_android))) {
                    mViewPager.setCurrentItem(1);
                    Utilities.addArticleToFragment(mFragments[1], article);
                    Log.d(TAG, "Added article to Android List.");
                } else if (article.getTopic().equalsIgnoreCase(getString(R.string.topic_webdev))) {
                    mViewPager.setCurrentItem(2);
                    Utilities.addArticleToFragment(mFragments[2], article);
                    Log.d(TAG, "Added article to WebDev List.");
                }
            }
        }
    }
}
