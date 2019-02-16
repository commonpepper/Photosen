package com.commonpepper.photosen.ui.fragments;

import android.os.Bundle;

import com.commonpepper.photosen.ui.viewmodels.SearchFragmentViewModelFactory;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class SearchListFragment extends AbstractListFragment {
    private static final String TAG_QUERY = "query";

    public static SearchListFragment newInstance(String query) {
        SearchListFragment newFragment = new SearchListFragment();
        Bundle args = new Bundle();
        args.putString(TAG_QUERY, query);
        newFragment.setArguments(args);
        return newFragment;
    }

    public SearchListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        String query = args.getString(TAG_QUERY);
        SearchFragmentViewModelFactory factory = new SearchFragmentViewModelFactory(query);

        mViewModel = ViewModelProviders.of(this, factory).get(SearchFragmentViewModelFactory.SearchListFragmentViewModel.class);
    }
}
