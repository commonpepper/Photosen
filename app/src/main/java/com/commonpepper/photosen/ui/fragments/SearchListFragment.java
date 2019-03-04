package com.commonpepper.photosen.ui.fragments;

import android.os.Bundle;

import com.commonpepper.photosen.ui.viewmodels.SearchFragmentViewModelFactory;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class SearchListFragment extends AbstractListFragment {
    private static final String TAG_QUERY = "query";
    private static final String TAG_TAGS = "tags";
    private static final String TAG_SORT = "sort";

    public static SearchListFragment newInstance(String query, String tags, String sort) {
        SearchListFragment newFragment = new SearchListFragment();
        Bundle args = new Bundle();
        args.putString(TAG_QUERY, query);
        args.putString(TAG_TAGS, tags);
        args.putString(TAG_SORT, sort);
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
        String tags = args.getString(TAG_TAGS);
        String sort = args.getString(TAG_SORT);
        SearchFragmentViewModelFactory factory = new SearchFragmentViewModelFactory(query, tags, sort);

        mViewModel = ViewModelProviders.of(this, factory).get(SearchFragmentViewModelFactory.SearchListFragmentViewModel.class);
    }
}
