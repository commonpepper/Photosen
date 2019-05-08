package com.commonpepper.photosen.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.commonpepper.photosen.R;
import com.commonpepper.photosen.network.NetworkState;
import com.commonpepper.photosen.ui.adapters.CommentsAdapter;
import com.commonpepper.photosen.ui.viewmodels.CommentsViewModelFactory;
import com.google.android.material.card.MaterialCardView;

public class CommentsFragment extends Fragment {
    public static final String TAG_PHOTO_ID = "PHOTO_ID";

    private CommentsViewModelFactory.CommentsViewModel mViewModel;
    private Button showComments;
    private Button refreshButton;
    private TextView errorTextView;
    private ProgressBar progressBar;
    private LinearLayout progressLayout;
    private RecyclerView recyclerView;
    private CardView cardView;

    public static CommentsFragment newInstance(String photo_id) {
        CommentsFragment newFragment = new CommentsFragment();
        Bundle args = new Bundle();
        args.putString(TAG_PHOTO_ID, photo_id);
        newFragment.setArguments(args);
        return newFragment;
    }

    public CommentsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        String photo_id = args.getString(TAG_PHOTO_ID);
        CommentsViewModelFactory factory = new CommentsViewModelFactory(photo_id);

        mViewModel = ViewModelProviders.of(this, factory).get(CommentsViewModelFactory.CommentsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comments, container, false);

        showComments = view.findViewById(R.id.show_comments_button);
        progressLayout = view.findViewById(R.id.comments_progress_layout);
        progressBar = view.findViewById(R.id.item_last_progressBar);
        errorTextView = view.findViewById(R.id.item_last_error_textView);
        refreshButton = view.findViewById(R.id.item_last_refreshButton);
        recyclerView = view.findViewById(R.id.comments_recycler);
        cardView = view.findViewById(R.id.comments_card_view);

        refreshButton.setOnClickListener(v -> mViewModel.loadComments());
        showComments.setOnClickListener(v -> mViewModel.loadComments());

        progressLayout.setVisibility(View.GONE);
        cardView.setVisibility(View.GONE);
        showComments.setVisibility(View.VISIBLE);

        mViewModel.getNetworkState().observe(this, networkState -> {
            if (networkState == NetworkState.FAILED) {
                cardView.setVisibility(View.GONE);
                showComments.setVisibility(View.GONE);
                progressLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                errorTextView.setVisibility(View.VISIBLE);
                refreshButton.setVisibility(View.VISIBLE);
            } else if (networkState == NetworkState.SUCCESS) {
                cardView.setVisibility(View.VISIBLE);
                showComments.setVisibility(View.GONE);
                progressLayout.setVisibility(View.GONE);
            } else if (networkState == NetworkState.RUNNING){
                cardView.setVisibility(View.GONE);
                showComments.setVisibility(View.GONE);
                progressLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                errorTextView.setVisibility(View.GONE);
                refreshButton.setVisibility(View.GONE);
            }
        });

        mViewModel.getComments().observe(this, comments -> {
            CommentsAdapter adapter = new CommentsAdapter(comments);
            adapter.setHasStableIds(true);
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            llm.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(llm);
            recyclerView.setItemViewCacheSize(10);
            recyclerView.setDrawingCacheEnabled(true);
            recyclerView.setAdapter(adapter);
        });

        return view;
    }
}
