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
import com.google.firebase.analytics.FirebaseAnalytics;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
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
