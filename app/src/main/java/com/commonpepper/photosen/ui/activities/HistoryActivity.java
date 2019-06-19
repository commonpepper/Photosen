package com.commonpepper.photosen.ui.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.commonpepper.photosen.Photosen;
import com.commonpepper.photosen.R;
import com.commonpepper.photosen.ui.adapters.HistoryAdapter;
import com.commonpepper.photosen.ui.viewmodels.HistoryActivityViewModel;
import com.google.android.material.navigation.NavigationView;

import java.util.concurrent.Executors;

public class HistoryActivity extends AbstractNavActivity {

    private HistoryActivityViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Toolbar toolbar = findViewById(R.id.about_toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.history));

        navigationView.getMenu().findItem(R.id.drawer_history).setCheckable(true);
        navigationView.getMenu().findItem(R.id.drawer_history).setChecked(true);
        navigationView.setNavigationItemSelectedListener(this);

        viewModel = ViewModelProviders.of(this).get(HistoryActivityViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.images_recyclerView);

        HistoryAdapter adapter = new HistoryAdapter();
        viewModel.getPhotosList().observe(this, adapter::submitList);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.history_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.clear_history) {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.clear_history))
                    .setMessage(getString(R.string.clear_confirm))
                    .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                        Executors.newSingleThreadExecutor().execute(() -> Photosen.getInstance().getDatabase().getHistoryDao().clear());
                    })
                    .setNegativeButton(android.R.string.no, null).show();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
