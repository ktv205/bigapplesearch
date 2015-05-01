package com.example.krishnateja.bigapplesearch.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.krishnateja.bigapplesearch.R;
import com.example.krishnateja.bigapplesearch.fragment.MainFragment;
import com.example.krishnateja.bigapplesearch.utils.RecentSearchContentProvider;
import com.example.krishnateja.bigapplesearch.fragment.LeftDrawerFragment;
import com.example.krishnateja.bigapplesearch.fragment.RightDrawerFragment;
import com.example.krishnateja.bigapplesearch.utils.rightdrawerutils.RightDrawerRecyclerAdapter;


public class MainActivity extends ActionBarActivity implements RightDrawerRecyclerAdapter.Filters {
    private LeftDrawerFragment mLeftDrawerFragment;
    private RightDrawerFragment mRightDrawerFragment;
    private MainFragment mMainFragment;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private Context mApplicationContext;
    private Activity mActivityContext;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int START_FLAG = 1;
    private static final int END_FLAG = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mApplicationContext = getApplicationContext();
        mActivityContext = MainActivity.this;
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    RecentSearchContentProvider.AUTHORITY, RecentSearchContentProvider.MODE);
            suggestions.saveRecentQuery(query, null);
        }
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        mLeftDrawerFragment = (LeftDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.main_left_drawer_fragment);
        mRightDrawerFragment = (RightDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.main_right_drawer_fragment);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        mLeftDrawerFragment.getDrawerLayout(drawerLayout);
        mRightDrawerFragment.getDrawerLayout(drawerLayout);
        mMainFragment=(MainFragment)getSupportFragmentManager().findFragmentById(R.id.main_container_fragment);
        setUp(drawerLayout, mToolbar);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        return true;
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            openOrClose(START_FLAG);
        } else if (id == R.id.action_right_nav) {
            openOrClose(END_FLAG);
        }
        return true;
    }

    public void setUp(final DrawerLayout drawerLayout, Toolbar toolbar) {
        mDrawerLayout = drawerLayout;
        mToolbar = toolbar;
        mActionBarDrawerToggle = new ActionBarDrawerToggle(mActivityContext, drawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

            }
        };


        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();
    }

    public void openOrClose(int flag) {
        if (flag == START_FLAG) {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            } else {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        } else {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.END)) {
                mDrawerLayout.closeDrawer(GravityCompat.END);
            } else {
                mDrawerLayout.openDrawer(GravityCompat.END);
            }
        }
    }

    @Override
    public void getFilters(int[] filters) {
        Log.d(TAG, "get filter in main activity");
        mMainFragment.changeDataSet(filters);


    }
}
