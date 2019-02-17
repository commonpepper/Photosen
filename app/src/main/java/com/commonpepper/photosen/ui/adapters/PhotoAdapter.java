package com.commonpepper.photosen.ui.adapters;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.commonpepper.photosen.R;
import com.commonpepper.photosen.network.NetworkState;
import com.commonpepper.photosen.network.datasource.AbstractListDataSource;
import com.commonpepper.photosen.network.model.Photo;
import com.commonpepper.photosen.ui.activities.SinglePhotoActivity;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.util.Random;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class PhotoAdapter extends PagedListAdapter<Photo, RecyclerView.ViewHolder> {
    private static final String TAG = PhotoAdapter.class.getSimpleName();

    private static final int VIEW_TYPE_PHOTO = 1;
    private static final int VIEW_TYPE_EXTRA = 2;

    private NetworkState networkState = NetworkState.RUNNING;

    public PhotoAdapter() {
        super(Photo.DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == VIEW_TYPE_PHOTO) {
            return new PhotoItemViewHolder(layoutInflater.inflate(R.layout.item_image, parent, false));
        } else {
            return new NetworkItemViewHolder(layoutInflater.inflate(R.layout.item_extra, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (networkState != NetworkState.SUCCESS && position == getItemCount() - 1) {
            return VIEW_TYPE_EXTRA;
        } else {
            return VIEW_TYPE_PHOTO;
        }
    }

    @Override
    public int getItemCount() {
        if (hasExtraRow()) {
            return super.getItemCount() + 1;
        } else {
            return super.getItemCount();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PhotoItemViewHolder) {
            ((PhotoItemViewHolder) holder).bind(getItem(position));
        } else if (holder instanceof NetworkItemViewHolder) {
            ((NetworkItemViewHolder) holder).bind(networkState);
        }
    }


    private boolean hasExtraRow() {
        return networkState != null && networkState != NetworkState.SUCCESS;
    }

    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = networkState;
        boolean hadExtraRow = hasExtraRow();
        networkState = newNetworkState;
        boolean hasExtraRow = hasExtraRow();
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }

    private class PhotoItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;

        public PhotoItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.item_image_imageView);
        }

        public void bind(Photo photo) {
            GradientDrawable gd = new GradientDrawable();
            gd.setSize(photo.getWidth_z(), photo.getHeight_z());
            gd.setShape(GradientDrawable.RECTANGLE);
            TypedArray colors = mImageView.getResources().obtainTypedArray(R.array.scroll_colors);
            int index = (int) (Math.random() * colors .length());
            int color = colors.getColor(index, Color.BLACK);
            gd.setColor(color);
            colors.recycle();

            Picasso.get().load(photo.getUrl_z()).placeholder(gd).into(mImageView);

            mImageView.setOnClickListener(v -> {
                Intent intent = new Intent(mImageView.getContext(), SinglePhotoActivity.class);
                intent.putExtra(SinglePhotoActivity.PHOTO_TAG, photo);
                Bundle options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity)mImageView.getContext(),
                        mImageView, "sharedImageView").toBundle();
                ActivityCompat.startActivity(mImageView.getContext(), intent, options);
            });
        }
    }

    private class NetworkItemViewHolder extends RecyclerView.ViewHolder {

        private ProgressBar mProgressBar;
        private TextView mTextView;
        private MaterialButton mButton;

        public NetworkItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mProgressBar = itemView.findViewById(R.id.item_last_progressBar);
            mTextView = itemView.findViewById(R.id.item_last_error_textView);
            mButton = itemView.findViewById(R.id.item_last_refreshButton);

            mButton.setOnClickListener(v -> {
                PagedList<Photo> list = getCurrentList();
                if (list != null) {
                    if (list.size() == 0) {
                        list.getDataSource().invalidate();
                    } else {
                        ((AbstractListDataSource) list.getDataSource()).retryLast();
                    }
                }
            });
        }

        public void bind(NetworkState networkState) {
            if (networkState == NetworkState.RUNNING) {
                mProgressBar.setVisibility(View.VISIBLE);
                mTextView.setVisibility(View.GONE);
                mButton.setVisibility(View.GONE);
            } else {
                mProgressBar.setVisibility(View.GONE);
                mTextView.setVisibility(View.VISIBLE);
                mButton.setVisibility(View.VISIBLE);
            }
        }
    }
}
