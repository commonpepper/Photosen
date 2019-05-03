package com.commonpepper.photosen.ui.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.commonpepper.photosen.ui.viewmodels.UserPhotosFragmentViewModelFactory;

public class UserPhotosListFragment extends AbstractListFragment {
    private static final String TAG_USER_ID = "user_id";

    public static UserPhotosListFragment newInstance(String user_id) {
        UserPhotosListFragment newFragment = new UserPhotosListFragment();
        Bundle args = new Bundle();
        args.putString(TAG_USER_ID, user_id);
        newFragment.setArguments(args);
        return newFragment;
    }

    public UserPhotosListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        String user_id = args.getString(TAG_USER_ID);
        UserPhotosFragmentViewModelFactory factory = new UserPhotosFragmentViewModelFactory(user_id);

        mViewModel = ViewModelProviders.of(this, factory).get(UserPhotosFragmentViewModelFactory.UserPhotosListFragmentViewModel.class);
    }
}
