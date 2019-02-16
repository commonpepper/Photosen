package com.commonpepper.photosen.ui.fragments;

import android.os.Bundle;

import com.commonpepper.photosen.ui.viewmodels.PhotoFragmentViewModelFactory;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class PhotoListFragment extends AbstractListFragment {
    private static final String TAG_DATE = "date";

    public static PhotoListFragment newInstance(String date) {
        PhotoListFragment newFragment = new PhotoListFragment();
        Bundle args = new Bundle();
        args.putString(TAG_DATE, date);
        newFragment.setArguments(args);
        return newFragment;
    }

    public PhotoListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        String order_by = args.getString(TAG_DATE);
        PhotoFragmentViewModelFactory factory = new PhotoFragmentViewModelFactory(order_by);

        mViewModel = ViewModelProviders.of(this, factory).get(PhotoFragmentViewModelFactory.PhotoListFragmentViewModel.class);
    }
}
