package com.commonpepper.photosen.ui.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.commonpepper.photosen.BuildConfig;
import com.commonpepper.photosen.Photosen;
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
                findViewById(R.id.layout_about_introduction),
                findViewById(R.id.layout_about_version),
                findViewById(R.id.layout_about_github),
                findViewById(R.id.layout_about_flickr),
                findViewById(R.id.layout_about_rate),
                findViewById(R.id.layout_about_retrofit),
                findViewById(R.id.layout_about_picasso),
                findViewById(R.id.layout_about_photoview),
                findViewById(R.id.layout_about_image_cropper),
                findViewById(R.id.layout_about_taptargetview),
                findViewById(R.id.layout_about_androidrate),
                findViewById(R.id.layout_about_appintro),
                findViewById(R.id.layout_about_expandable)
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
            case R.id.layout_about_introduction:
                Intent intent = new Intent(this, IntroActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_about_github:
                openUrl("https://" + getResources().getString(R.string.github_link));
                break;
            case R.id.layout_about_flickr:
                openUrl("https://flickr.com");
                break;
            case R.id.layout_about_rate:
                Photosen.firebaseAnalytics.logEvent("ABOUT_RATE", null);
                Uri uri = Uri.parse("market://details?id=" + Photosen.PACKAGE_NAME);
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + Photosen.PACKAGE_NAME)));
                }
                break;
            case R.id.layout_about_retrofit:
                openUrl("https://github.com/square/retrofit");
                break;
            case R.id.layout_about_picasso:
                openUrl("https://github.com/square/picasso");
                break;
            case R.id.layout_about_photoview:
                openUrl("https://github.com/chrisbanes/PhotoView");
                break;
            case R.id.layout_about_image_cropper:
                openUrl("https://github.com/ArthurHub/Android-Image-Cropper");
                break;
            case R.id.layout_about_taptargetview:
                openUrl("https://github.com/KeepSafe/TapTargetView");
                break;
            case R.id.layout_about_androidrate:
                openUrl("https://github.com/Vorlonsoft/AndroidRate");
                break;
            case R.id.layout_about_appintro:
                openUrl("https://github.com/AppIntro/AppIntro");
                break;
            case R.id.layout_about_expandable:
                openUrl("https://github.com/cachapa/ExpandableLayout");
                break;
        }
    });

    private void openUrl(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
