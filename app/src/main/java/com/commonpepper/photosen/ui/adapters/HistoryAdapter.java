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
import com.commonpepper.photosen.model.Photo;
import com.commonpepper.photosen.ui.activities.SinglePhotoActivity;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryAdapter extends PagedListAdapter<Photo, RecyclerView.ViewHolder> {
    private static final String TAG = PhotoAdapter.class.getSimpleName();

    public HistoryAdapter() {
        super(Photo.DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new PhotoItemViewHolder(layoutInflater.inflate(R.layout.item_image, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((PhotoItemViewHolder) holder).bind(getItem(position));
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
            int index = (int) (Math.random() * colors.length());
            int color = colors.getColor(index, Color.BLACK);
            gd.setColor(color);
            colors.recycle();

            Picasso.get().load(photo.getUrl_z()).placeholder(gd).into(mImageView);
            mImageView.setOnClickListener(v -> {
                Intent intent = new Intent(mImageView.getContext(), SinglePhotoActivity.class);
                intent.putExtra(SinglePhotoActivity.PHOTO_TAG, photo);
                intent.putExtra(SinglePhotoActivity.SAVE_HISTORY_TAG, false);
                Bundle options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mImageView.getContext(),
                        mImageView, "sharedImageView").toBundle();
                ActivityCompat.startActivity(mImageView.getContext(), intent, options);
            });

        }
    }
}
