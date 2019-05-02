package com.commonpepper.photosen.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

import com.commonpepper.photosen.R;
import com.commonpepper.photosen.network.NetworkState;
import com.commonpepper.photosen.network.model.Photo;
import com.commonpepper.photosen.network.model.PhotoDetails;
import com.commonpepper.photosen.network.model.PhotoSizes;
import com.commonpepper.photosen.ui.activities.SearchActivity;
import com.commonpepper.photosen.ui.viewmodels.PhotoDetailsViewModelFactory;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private LinearLayout tagsLayout;
    private TextView tagsLabel;
    private PhotoDetails details;
    private MutableLiveData<PhotoSizes> photoSizes = new MutableLiveData<>();

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
        tagsLayout = view.findViewById(R.id.single_photo_tags_layout);
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

        mViewModel.getPhotoSizes().observe(this, x -> {
            photoSizes.postValue(x);
            if (x.getSizes().getSize().size() > 0) {
                int width = x.getSizes().getSize().get(x.getSizes().getSize().size() - 1).getWidth();
                int height = x.getSizes().getSize().get(x.getSizes().getSize().size() - 1).getHeight();
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
        details = photoDetails;

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

        List<Chip> chips = new ArrayList<>();
        for (PhotoDetails.PhotoBean.TagsBean.TagBean tag : tags) {
            Chip chip = new Chip(getContext());
            chip.setTransitionName("sharedChip");
            chip.setText(tag.getRaw());
            chip.setOnClickListener(v -> {
                Intent intent = new Intent(PhotoDetailsFragment.this.getContext(), SearchActivity.class);
                intent.putExtra(SearchActivity.TAG_SEARCHTAG, tag.getRaw());
                Bundle options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) PhotoDetailsFragment.this.getContext(),
                        chip, "sharedChip").toBundle();
                ActivityCompat.startActivity(PhotoDetailsFragment.this.getContext(), intent, options);
            });

            chips.add(chip);
        }

        simpleBinPack(chips);
    }

    private void showFailed() {
        progressLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        errorTextView.setVisibility(View.VISIBLE);
        refreshButton.setVisibility(View.VISIBLE);
        detailsLayout.setVisibility(View.GONE);
    }

    public LiveData<PhotoSizes> getLiveSizes() {
        return photoSizes;
    }

    private void simpleBinPack(List<Chip> chips) {
        List<List<Chip>> containers = new ArrayList<>();
        List<Integer> freeSpace = new ArrayList<>();

        Collections.sort(chips, (x, y) -> -(Integer.compare(x.getChipDrawable().getIntrinsicWidth(),
                y.getChipDrawable().getIntrinsicWidth())));

        for (Chip chip : chips) {
            int chipWidth = chip.getChipDrawable().getIntrinsicWidth();
            int resultingContainer = -1;
            for (int i = 0; i < containers.size(); i++) {
                if (chipWidth < freeSpace.get(i)) {
                    resultingContainer = i;
                }
            }
            if (resultingContainer != -1) {
                containers.get(resultingContainer).add(chip);
                freeSpace.set(resultingContainer, freeSpace.get(resultingContainer) - chipWidth);
            } else {
                ArrayList<Chip> newContainer = new ArrayList<>();
                newContainer.add(chip);
                containers.add(newContainer);
                freeSpace.add(getContext().getResources().getDisplayMetrics().widthPixels - chipWidth);
            }
        }

        if (containers.size() > 0) {
            for (int i = 0; i < containers.size() - 1; i++) {
//                Log.d("CONTAINER SIZE:", "" + containers.get(i).size());
                FlexboxLayout flexbox = getNewFlexbox();
                flexbox.setJustifyContent(JustifyContent.SPACE_AROUND);
                for (Chip chip : containers.get(i)) {
                    flexbox.addView(chip);
                }
                tagsLayout.addView(flexbox);
            }
            //last row
            FlexboxLayout flexbox = getNewFlexbox();
            flexbox.setJustifyContent(JustifyContent.FLEX_START);
            for (Chip chip : containers.get(containers.size() - 1)) {
                flexbox.addView(chip);
            }
            tagsLayout.addView(flexbox);
        }
    }

    private FlexboxLayout getNewFlexbox() {
        FlexboxLayout flexbox = new FlexboxLayout(getContext());
        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                FlexboxLayout.LayoutParams.MATCH_PARENT,
                FlexboxLayout.LayoutParams.WRAP_CONTENT);
        params.setLayoutDirection(FlexDirection.ROW);
        flexbox.setLayoutParams(params);
        flexbox.setFlexWrap(FlexWrap.NOWRAP);
        return flexbox;
    }
}
