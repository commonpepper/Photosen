package com.commonpepper.photosen.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.commonpepper.photosen.R;
import com.commonpepper.photosen.ui.adapters.MyPagerAdapter;
import com.commonpepper.photosen.ui.fragments.PhotoListFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.vorlonsoft.android.rate.AppRate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AbstractNavActivity {

    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private MyPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar);
        mViewPager = findViewById(R.id.view_pager);
        mTabLayout = findViewById(R.id.tab_layout);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);

        navigationView.getMenu().findItem(R.id.drawer_popular).setCheckable(true);
        navigationView.getMenu().findItem(R.id.drawer_popular).setChecked(true);
        navigationView.setNavigationItemSelectedListener(this);

        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);

        PhotoListFragment popularNowPhotoFragment = PhotoListFragment.newInstance(null);
        mPagerAdapter.addFragment(popularNowPhotoFragment, getString(R.string.popular) + " " + getString(R.string.now));

        DateFormat dateFormatApi = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dateFormatTitle = new SimpleDateFormat("dd.MM");
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        for (int i = 0; i < 7; i++) {
            cal.add(Calendar.DATE, -1);
            PhotoListFragment latestPhotoFragment = PhotoListFragment.newInstance(dateFormatApi.format(cal.getTime()));
            mPagerAdapter.addFragment(latestPhotoFragment, getString(R.string.popular) + " "
                    + dateFormatTitle.format(cal.getTime()));
        }

        mTabLayout.setupWithViewPager(mViewPager);

        AppRate.with(this)
                .setThemeResId(R.style.DialogTheme)
                .setInstallDays((byte) 0)                  // default is 10, 0 means install day, 10 means app is launched 10 or more days later than installation
                .setLaunchTimes((byte) 3)                  // default is 10, 3 means app is launched 3 or more times
                .setRemindInterval((byte) 1)               // default is 1, 1 means app is launched 1 or more days after neutral button clicked
                .setRemindLaunchesNumber((byte) 0)         // default is 0, 1 means app is launched 1 or more times after neutral button clicked
                .monitor();                                // Monitors the app launch times
        AppRate.showRateDialogIfMeetsConditions(this); // Shows the Rate Dialog when conditions are met
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search_button) {
            startActivity(new Intent(this, SearchActivity.class));
        } else if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }
}
