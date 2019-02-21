package com.commonpepper.photosen.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.commonpepper.photosen.R;
import com.commonpepper.photosen.ui.fragments.SearchListFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashSet;
import java.util.Set;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SearchActivity extends AppCompatActivity {
    public static final String TAG_SEARCHTAG = "search_tag";

    private EditText mEditText;
    private ChipGroup chipGroup;
    private Chip firstChip;

    private Set<String> tags = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mEditText = findViewById(R.id.search_edit_text);
        Toolbar toolbar = findViewById(R.id.search_toolbar);
        chipGroup = findViewById(R.id.search_chip_group);
        firstChip = findViewById(R.id.search_first_chip);
        TextInputEditText inputTag = findViewById(R.id.tag_input);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        Intent intent = getIntent();
        String firstTag = intent.getStringExtra(TAG_SEARCHTAG);
        if (firstTag != null) {
            tags.add(firstTag);
            firstChip.setText(firstTag);
            firstChip.setOnCloseIconClickListener(x -> {
                chipGroup.removeView(firstChip);
                tags.remove(firstChip.getText().toString());
            });
            doSearch();
        } else {
            chipGroup.removeView(firstChip);
        }

        mEditText.setOnEditorActionListener((v, actionId, event) -> {
            doSearch();
            return true;
        });

        inputTag.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE ||
                    event != null && event.getAction() == KeyEvent.ACTION_DOWN
                            && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                if (event == null || !event.isShiftPressed()) {
                    String newTag = inputTag.getText().toString();
                    if (newTag.length() > 0 && !tags.contains(newTag)) {
                        Chip chip = new Chip(SearchActivity.this);
                        chip.setText(newTag);
                        tags.add(newTag);
                        chip.setLayoutDirection(View.LAYOUT_DIRECTION_LOCALE);
                        chip.setCloseIconVisible(true);
                        chip.setOnCloseIconClickListener(x -> {
                            chipGroup.removeView(chip);
                            tags.remove(chip.getText().toString());
                            doSearch();
                        });
                        chipGroup.addView(chip);
                        inputTag.setText("");
                        doSearch();
                    }
                    return true;
                }
            }
            return false;
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mEditText.getText().toString().length() > 0) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }
    }

    private void doSearch() {
        String query = mEditText.getText().toString();
        if (query.length() == 0) query = null;

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.search_linear_layout, SearchListFragment.newInstance(query, tagsToString()))
                .commit();

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private String tagsToString() {
        StringBuilder ans = new StringBuilder();
        for (String tag : tags) {
            ans.append(tag);
            ans.append(',');
        }
        return ans.toString();
    }
}
