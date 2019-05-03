package com.commonpepper.photosen.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.commonpepper.photosen.R;
import com.commonpepper.photosen.ui.fragments.UserPhotosListFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

public class UserActivity extends AbstractNavActivity {
    public static final String USER_ID_TAG = "USER_ID_TAG";
    public static final String USERNAME_TAG = "USERNAME_TAG";
    public static final String ICON_URL_TAG = "ICON_URL_TAG";

    UserPhotosListFragment fragment;

    public static Intent getStartingIntent(Context context, String user_id, String username, String iconUrl) {
        Intent intent = new Intent(context, UserActivity.class);
        intent.putExtra(USER_ID_TAG, user_id);
        intent.putExtra(USERNAME_TAG, username);
        intent.putExtra(ICON_URL_TAG, iconUrl);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Toolbar toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);

        String user_id = getIntent().getStringExtra(USER_ID_TAG);
        String username = getIntent().getStringExtra(USERNAME_TAG);
        String iconUrl = getIntent().getStringExtra(ICON_URL_TAG);

        getSupportActionBar().setTitle(username);

        ImageView avatar = findViewById(R.id.avatar);
        Picasso.get().load(iconUrl).into(avatar);

        FloatingActionButton fab = findViewById(R.id.openinbrowser_fab);
        fab.setOnClickListener(v -> {
            openInBrowser("https://flickr.com/photos/" + user_id);
        });

        if (getSupportFragmentManager().getFragments().size() == 0) {
            fragment = UserPhotosListFragment.newInstance(user_id);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.user_photos_fragment_layout, fragment)
                    .commit();
        } else {
            fragment = (UserPhotosListFragment) getSupportFragmentManager().getFragments().get(0);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void openInBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
