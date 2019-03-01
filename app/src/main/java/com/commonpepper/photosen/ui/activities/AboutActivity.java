package com.commonpepper.photosen.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.commonpepper.photosen.BuildConfig;
import com.commonpepper.photosen.R;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

public class AboutActivity extends AbstractNavActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = findViewById(R.id.about_toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        TextView versionView = findViewById(R.id.version_view);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.about));

        navigationView.getMenu().findItem(R.id.drawer_about).setCheckable(true);
        navigationView.getMenu().findItem(R.id.drawer_about).setChecked(true);
        navigationView.setNavigationItemSelectedListener(this);

        versionView.setText(BuildConfig.VERSION_NAME);

        LinearLayout[] containers = new LinearLayout[]{
                findViewById(R.id.layout_about_version),
                findViewById(R.id.layout_about_github)
        };
        for (LinearLayout r : containers) {
            r.setOnClickListener(clickListener);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private View.OnClickListener clickListener = (view -> {
        switch (view.getId()) {
            case R.id.layout_about_github:
                openInBrowser("https://" + getResources().getString(R.string.github_link));
                break;
        }
    });

    private void openInBrowser(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
