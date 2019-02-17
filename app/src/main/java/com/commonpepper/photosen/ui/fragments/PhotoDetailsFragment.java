package com.commonpepper.photosen.ui.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.commonpepper.photosen.R;
import com.commonpepper.photosen.network.NetworkState;
import com.commonpepper.photosen.network.model.Photo;
import com.commonpepper.photosen.network.model.PhotoDetails;
import com.commonpepper.photosen.network.model.PhotoSizes;
import com.commonpepper.photosen.ui.viewmodels.PhotoDetailsViewModelFactory;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class PhotoDetailsFragment extends Fragment {
    private static final String TAG_PHOTO = "photo";

    private PhotoDetailsViewModelFactory.PhotoDetailsViewModel mViewModel;
    private LinearLayout progressLayout;
    private LinearLayout detailsLayout;
    private ProgressBar progressBar;
    private TextView errorTextView;
    private MaterialButton refreshButton;
    private TextView username;
    private TextView viewsNumber;
    private ImageView imageViewAvatar;
    private TextView location;
    private LinearLayout locationLayout;
    private TextView textViewWidth;
    private TextView textViewHeigth;
    private TextView textViewDate;
    private Photo photo;
    private ChipGroup chipGroup;
    private TextView tagsLabel;

    public static PhotoDetailsFragment newInstance(Photo photo) {
        PhotoDetailsFragment newFragment = new PhotoDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(TAG_PHOTO, photo);
        newFragment.setArguments(args);
        return newFragment;
    }

    public PhotoDetailsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        photo = args.getParcelable(TAG_PHOTO);
        PhotoDetailsViewModelFactory factory = new PhotoDetailsViewModelFactory(photo.getId(), photo.getSecret());

        mViewModel = ViewModelProviders.of(this, factory).get(PhotoDetailsViewModelFactory.PhotoDetailsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        progressLayout = view.findViewById(R.id.single_image_progress_layout);
        progressBar = progressLayout.findViewById(R.id.item_last_progressBar);
        errorTextView = progressLayout.findViewById(R.id.item_last_error_textView);
        refreshButton = progressLayout.findViewById(R.id.item_last_refreshButton);
        refreshButton.setOnClickListener(v -> mViewModel.loadDetails());

        detailsLayout = view.findViewById(R.id.single_image_details_layout);
        username = view.findViewById(R.id.single_image_username);
        viewsNumber = view.findViewById(R.id.single_image_views_number);
        imageViewAvatar = view.findViewById(R.id.single_photo_avatar_image_view);
        location = view.findViewById(R.id.single_image_location);
        locationLayout = view.findViewById(R.id.single_image_location_layout);
        textViewWidth = view.findViewById(R.id.single_image_width);
        textViewHeigth = view.findViewById(R.id.single_image_height);
        textViewDate = view.findViewById(R.id.single_image_date);
        chipGroup = view.findViewById(R.id.details_chip_group);
        tagsLabel = view.findViewById(R.id.tags_label);

        showRunning();

        mViewModel.getNetworkState().observe(this, networkState -> {
            if (networkState == NetworkState.RUNNING) {
                showRunning();
            } else if (networkState == NetworkState.FAILED) {
                showFailed();
            } //else SUCCESS
        });

        mViewModel.getPhotoDetails().observe(this, this::showSuccess);

        mViewModel.getPhotoSizes().observe(this, photoSizes -> {
            if (photoSizes.getSizes().getSize().size() > 0) {
                int width = photoSizes.getSizes().getSize().get(photoSizes.getSizes().getSize().size() - 1).getWidth();
                int height = photoSizes.getSizes().getSize().get(photoSizes.getSizes().getSize().size() - 1).getHeight();
                textViewWidth.setText(getResources().getQuantityString(R.plurals.x_pixels, width, width));
                textViewHeigth.setText(getResources().getQuantityString(R.plurals.x_pixels, height, height));
            }
        });

        return view;
    }

    private void showRunning() {
        progressLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        errorTextView.setVisibility(View.GONE);
        refreshButton.setVisibility(View.GONE);
        detailsLayout.setVisibility(View.GONE);
    }

    private void showSuccess(PhotoDetails photoDetails) {
        progressLayout.setVisibility(View.GONE);
        detailsLayout.setVisibility(View.VISIBLE);

        Picasso.get().load(photo.getIconUrl()).into(imageViewAvatar);

        username.setText(photoDetails.getPhoto().getOwner().getUsername());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            username.setTextColor(getContext().getColor(R.color.colorAccent));
        }

        int views = photoDetails.getPhoto().getViews();
        viewsNumber.setText(getResources().getQuantityString(R.plurals.x_views, views, views));

        String locationStr = photoDetails.getPhoto().getOwner().getLocation();
        if (locationStr.length() > 0) {
            location.setText(locationStr);
        } else {
            locationLayout.setVisibility(View.GONE);
        }

        textViewDate.setText(photo.getDatetaken());

        List<PhotoDetails.PhotoBean.TagsBean.TagBean> tags = photoDetails.getPhoto().getTags().getTag();
        if (tags.isEmpty()) {
            tagsLabel.setVisibility(View.GONE);
        }
        for (PhotoDetails.PhotoBean.TagsBean.TagBean tag : tags) {
            Chip chip = new Chip(getContext());
            chip.setText(tag.getRaw());
            chip.setLayoutDirection(View.LAYOUT_DIRECTION_LOCALE);
            chipGroup.addView(chip);
        }
    }

    private void showFailed() {
        progressLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        errorTextView.setVisibility(View.VISIBLE);
        refreshButton.setVisibility(View.VISIBLE);
        detailsLayout.setVisibility(View.GONE);
    }
}
