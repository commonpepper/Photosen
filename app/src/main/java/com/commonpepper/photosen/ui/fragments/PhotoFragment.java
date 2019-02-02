package com.commonpepper.photosen.ui.fragments;

import android.os.Bundle;

import com.commonpepper.photosen.ui.viewmodels.PhotoFragmentViewModelFactory;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class PhotoFragment extends MyAbstractFragment {
    private static final String TAG_ORDER_BY = "order_by";

    public static PhotoFragment newInstance(String order_by) {
        PhotoFragment newFragment = new PhotoFragment();
        Bundle args = new Bundle();
        args.putString(TAG_ORDER_BY, order_by);
        newFragment.setArguments(args);
        return newFragment;
    }

    public PhotoFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        String order_by = args.getString(TAG_ORDER_BY);
        PhotoFragmentViewModelFactory factory = new PhotoFragmentViewModelFactory(order_by);

        mViewModel = ViewModelProviders.of(this, factory).get(PhotoFragmentViewModelFactory.PhotoFragmentViewModel.class);
    }
}
