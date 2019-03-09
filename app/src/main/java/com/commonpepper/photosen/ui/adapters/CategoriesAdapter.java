package com.commonpepper.photosen.ui.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.commonpepper.photosen.R;
import com.commonpepper.photosen.ui.activities.SearchActivity;
import com.commonpepper.photosen.ui.activities.SinglePhotoActivity;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {
    private static final String TAG = CategoriesAdapter.class.getSimpleName();

    private String[] categories;

    public CategoriesAdapter(String[] categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        CategoriesViewHolder viewHolder = new CategoriesViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
        holder.textView.setText(categories[position]);
        try {
            InputStream inputStream = holder.imageView.getContext().getAssets().open(categories[position] + ".jpg");
            holder.imageView.setImageDrawable(Drawable.createFromStream(inputStream, null));
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }
        holder.layout.setOnClickListener(v -> {
            Intent intent = new Intent(holder.textView.getContext(), SearchActivity.class);
            intent.putExtra(SearchActivity.TAG_SEARCHTAG, categories[position]);
            Bundle options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity)holder.textView.getContext(),
                    holder.textView, "sharedChip").toBundle();
            ActivityCompat.startActivity(holder.textView.getContext(), intent, options);
        });
    }

    @Override
    public int getItemCount() {
        return categories.length;
    }

    public static class CategoriesViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;
        public RelativeLayout layout;
        public CategoriesViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.category_image);
            textView = view.findViewById(R.id.category_text);
            layout = view.findViewById(R.id.category_layout);
        }
    }
}
