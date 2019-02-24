package com.commonpepper.photosen.ui.activities;

import android.content.Intent;
import android.view.MenuItem;

import com.commonpepper.photosen.R;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class AbstractNavActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    protected DrawerLayout drawerLayout;

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.drawer_search) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        } else if (id == R.id.drawer_popular) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        if (drawerLayout != null) drawerLayout.closeDrawers();
        return true;
    }
}
