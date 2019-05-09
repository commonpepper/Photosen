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
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.commonpepper.photosen.R;
import com.commonpepper.photosen.network.NetworkState;
import com.commonpepper.photosen.ui.adapters.CommentsAdapter;
import com.commonpepper.photosen.ui.viewmodels.CommentsViewModelFactory;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;

public class CommentsFragment extends Fragment implements ExpandableLayout.OnExpansionUpdateListener {
    public static final String TAG_PHOTO_ID = "PHOTO_ID";

    private CommentsViewModelFactory.CommentsViewModel mViewModel;
    private Button showComments;
    private Button refreshButton;
    private TextView errorTextView;
    private ProgressBar progressBar;
    private LinearLayout progressLayout;
    private RecyclerView recyclerView;
    private ExpandableLayout expandableLayout;
    private WeakReference<NestedScrollView> nestedScrollView;
    private Button hideComments;

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
        hideComments = view.findViewById(R.id.hide_comments_button);
        progressLayout = view.findViewById(R.id.comments_progress_layout);
        progressBar = view.findViewById(R.id.item_last_progressBar);
        errorTextView = view.findViewById(R.id.item_last_error_textView);
        refreshButton = view.findViewById(R.id.item_last_refreshButton);
        recyclerView = view.findViewById(R.id.comments_recycler);
        expandableLayout = view.findViewById(R.id.expandable_layout);

        refreshButton.setOnClickListener(v -> {
            mViewModel.loadComments();
        });
        showComments.setOnClickListener(v -> {
            mViewModel.hiden = false;
            showComments.setVisibility(View.GONE);
            hideComments.setVisibility(View.VISIBLE);
            expandableLayout.expand();
            if (mViewModel.getNetworkState().getValue() != NetworkState.SUCCESS) {
                mViewModel.loadComments();
            }
            expandableLayout.setOnExpansionUpdateListener(this);
        });
        hideComments.setOnClickListener(v -> {
            mViewModel.hiden = true;
            showComments.setVisibility(View.VISIBLE);
            hideComments.setVisibility(View.GONE);
            expandableLayout.collapse();
        });

        mViewModel.getNetworkState().observe(this, networkState -> {
            if (networkState == NetworkState.FAILED) {
                progressLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                errorTextView.setVisibility(View.VISIBLE);
                refreshButton.setVisibility(View.VISIBLE);
            } else if (networkState == NetworkState.SUCCESS) {
                progressLayout.setVisibility(View.GONE);
            } else if (networkState == NetworkState.RUNNING) {
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
//            recyclerView.setItemViewCacheSize(10);
//            recyclerView.setDrawingCacheEnabled(true);
            recyclerView.setAdapter(adapter);
        });

        //INITIALIZATION:
        if (mViewModel.hiden) {
            showComments.setVisibility(View.VISIBLE);
            hideComments.setVisibility(View.GONE);
            expandableLayout.collapse();
        } else {
            showComments.setVisibility(View.GONE);
            hideComments.setVisibility(View.VISIBLE);
            expandableLayout.expand();
        }

        return view;
    }

    public void setNestedScrollView(NestedScrollView nestedScrollView) {
        this.nestedScrollView = new WeakReference<>(nestedScrollView);
    }

    @Override
    public void onExpansionUpdate(float expansionFraction, int state) {
        if (state == ExpandableLayout.State.EXPANDING && nestedScrollView.get() != null) {
//            Log.d("ExpandableLayout:", "expansionFraction = " + expansionFraction);
            NestedScrollView nestedScroll = nestedScrollView.get();
            nestedScroll.fullScroll(View.FOCUS_DOWN);
        }
    }
}
