package com.commonpepper.photosen.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.commonpepper.photosen.R;
import com.commonpepper.photosen.ui.adapters.MyPagerAdapter;
import com.commonpepper.photosen.ui.fragments.PhotoListFragment;
import com.google.android.material.tabs.TabLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {

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

        setSupportActionBar(mToolbar);

        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);

        DateFormat dateFormatApi = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dateFormatTitle = new SimpleDateFormat("dd.MM");
        final Calendar cal = Calendar.getInstance();
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
        }

        return super.onOptionsItemSelected(item);
    }
}
