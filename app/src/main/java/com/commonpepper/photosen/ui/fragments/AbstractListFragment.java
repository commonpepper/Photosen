package com.commonpepper.photosen.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.commonpepper.photosen.R;
import com.commonpepper.photosen.network.NetworkState;
import com.commonpepper.photosen.ui.adapters.PhotoAdapter;
import com.commonpepper.photosen.ui.viewmodels.AbstractListFragmentViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AbstractListFragment extends Fragment {
    protected AbstractListFragmentViewModel mViewModel;
    protected RecyclerView mRecyclerView;
    protected PhotoAdapter mPhotoAdapter = new PhotoAdapter();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_images, container, false);
        mRecyclerView = view.findViewById(R.id.fragment_images_recyclerView);

        mViewModel.getPhotosList().observe(this, pagedList -> {
            mPhotoAdapter.submitList((PagedList) pagedList);
        });

        mViewModel.getNetworkState().observe(this, networkState -> {
            mPhotoAdapter.setNetworkState((NetworkState) networkState);
        });

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setAdapter(mPhotoAdapter);

        return view;
    }

    public void scrollToTop() {
        mRecyclerView.scrollToPosition(0);
    }
}

