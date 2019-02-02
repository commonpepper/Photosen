package com.commonpepper.photosen.ui.fragments;

import android.os.Bundle;

import com.commonpepper.photosen.ui.viewmodels.SearchFragmentViewModelFactory;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class SearchFragment extends MyAbstractFragment {
    private static final String TAG_QUERY = "order_by";

    public static SearchFragment newInstance(String query) {
        SearchFragment newFragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(TAG_QUERY, query);
        newFragment.setArguments(args);
        return newFragment;
    }

    public SearchFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        String query = args.getString(TAG_QUERY);
        SearchFragmentViewModelFactory factory = new SearchFragmentViewModelFactory(query);

        mViewModel = ViewModelProviders.of(this, factory).get(SearchFragmentViewModelFactory.SearchFragmentViewModel.class);
    }
}
