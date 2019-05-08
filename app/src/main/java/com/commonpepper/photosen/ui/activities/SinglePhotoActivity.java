package com.commonpepper.photosen.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.commonpepper.photosen.Photosen;
import com.commonpepper.photosen.R;
import com.commonpepper.photosen.network.DownloadService;
import com.commonpepper.photosen.network.model.Photo;
import com.commonpepper.photosen.network.model.PhotoSizes;
import com.commonpepper.photosen.ui.fragments.CommentsFragment;
import com.commonpepper.photosen.ui.fragments.PhotoDetailsFragment;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SinglePhotoActivity extends AbstractNavActivity {
    private static final String TAG = SinglePhotoActivity.class.getSimpleName();

    public static final String PHOTO_TAG = "PARCELABLE_PHOTO";
    public static final String PHOTOS_URL = "https://www.flickr.com/photos/";

    private static final String PREFERENCE_FIRST_LAUNCH = "SINGLE_PHOTO_FIRST_LAUNCH";

    private Photo photo;

    private DownloadService.Aciton action;
    private PhotoDetailsFragment detailsFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_photo);

        Toolbar toolbar = findViewById(R.id.single_image_toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);

        photo = getIntent().getParcelableExtra(PHOTO_TAG);

        TextView title = findViewById(R.id.single_photo_title);
        ImageView imageView = findViewById(R.id.single_photo_image_view);

        FloatingActionButton fabDownload = findViewById(R.id.fab_download);
        FloatingActionButton fabSetWallpaper = findViewById(R.id.fab_wallpaper);
        hideFAB(fabDownload);
        hideFAB(fabSetWallpaper);
        fabDownload.setOnClickListener(v -> {
            action = DownloadService.Aciton.DOWNLOAD_ONLY;
            if (Photosen.isStoragePermissionGranted(SinglePhotoActivity.this)) {
                downloadPhoto();
            }
        });
        fabSetWallpaper.setOnClickListener(v -> {
            action = DownloadService.Aciton.WALLPAPER;
            if (Photosen.isStoragePermissionGranted(SinglePhotoActivity.this)) {
                downloadPhoto();
            }
        });

        if (photo.getTitle() != null && photo.getTitle().length() > 0) {
            title.setText(photo.getTitle());
        } else {
            title.setVisibility(View.GONE);
        }

        Picasso.get().load(photo.getUrl_z()).into(imageView);

//        OLD VERSION:
//        if (getSupportFragmentManager().getFragments().size() == 0) {
//            detailsFragment = PhotoDetailsFragment.newInstance(photo);
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.single_image_details_layout, detailsFragment)
//                    .commit();
//        } else {
//            detailsFragment = (PhotoDetailsFragment) getSupportFragmentManager().getFragments().get(0);
//        }

//        LET'S GET FRAGMENT LINKS IN CASE OF SCREEN ROTATION
        CommentsFragment commentsFragment;
        boolean detailsFlag = false;
        boolean commentsFlag = false;
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment instanceof PhotoDetailsFragment) {
                detailsFragment = (PhotoDetailsFragment) fragment;
                detailsFlag = true;
            }
            if (fragment instanceof CommentsFragment) {
                commentsFragment = (CommentsFragment) fragment;
                commentsFlag = true;
            }
        }
        if (!detailsFlag) {
            detailsFragment = PhotoDetailsFragment.newInstance(photo);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.single_image_details_layout, detailsFragment)
                    .commit();
        }
        if (!commentsFlag) {
            commentsFragment = CommentsFragment.newInstance(photo.getId());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.single_image_comments_layout, commentsFragment)
                    .commit();
        }
//        END OF RESTORING FRAGMENT LINKS

        detailsFragment.getLiveSizes().observe(this, x -> {
            showFAB(fabDownload);
            showFAB(fabSetWallpaper);

            SharedPreferences prefs = getSharedPreferences(Photosen.PREFERENCES, MODE_PRIVATE);
            boolean firstLaunch = prefs.getBoolean(PREFERENCE_FIRST_LAUNCH, true);
            if (firstLaunch) {
                new TapTargetSequence(this).targets(
                        TapTarget.forView(fabDownload, getString(R.string.download_fab_hint))
                                .tintTarget(false),
                        TapTarget.forView(fabSetWallpaper, getString(R.string.set_as_wallpaper_fab_hint))
                                .tintTarget(false)
                ).continueOnCancel(true).start();
                prefs.edit().putBoolean(PREFERENCE_FIRST_LAUNCH, false).apply();
            }

            imageView.setOnClickListener(v -> {
                List<PhotoSizes.SizesBean.SizeBean> sizes = x.getSizes().getSize();
                String url = sizes.get(sizes.size() - 1).getSource();
                Intent intent = new Intent(SinglePhotoActivity.this, PreviewActivity.class);
                intent.putExtra(PreviewActivity.TAG_URL, url);
                startActivity(intent);
            });
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void openInBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.single_image_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.single_image_open_in_browser) {
            openInBrowser(PHOTOS_URL + photo.getOwner() + "/" + photo.getId());
        } else if (id == R.id.single_image_share) {
            shareUrl(PHOTOS_URL + photo.getOwner() + "/" + photo.getId());
        }

        return super.onOptionsItemSelected(item);
    }

    private void downloadPhoto() {
        if (!DownloadService.isRunning) {
            Toast.makeText(this, getString(R.string.download_started), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, DownloadService.class);
            if (detailsFragment.getLiveSizes().getValue() != null) { //shouldn't be null, when fabs are visible, but check just in case
                List<PhotoSizes.SizesBean.SizeBean> sizes = detailsFragment.getLiveSizes().getValue().getSizes().getSize();
                String url = sizes.get(sizes.size() - 1).getSource();
                intent.putExtra(DownloadService.TAG_URL, url);
                String[] urlSplit = url.split("\\.");
                String format = urlSplit[urlSplit.length - 1];
                if (!format.equals("jpg") && !format.equals("gif") && !format.equals("png"))
                    format = "jpg";
                intent.putExtra(DownloadService.TAG_FILENAME, photo.getId() + "." + format);
                intent.putExtra(DownloadService.TAG_ACTION, action);
                startService(intent);
            }
        } else {
            Toast.makeText(this, getString(R.string.please_wait_for_current_download), Toast.LENGTH_SHORT).show();
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
            if (action != null) downloadPhoto();
        } else {
            Toast.makeText(this, getString(R.string.no_permisson), Toast.LENGTH_SHORT).show();
        }
    }

    private void shareUrl(String url) {
        if (photo != null) {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

            share.putExtra(Intent.EXTRA_SUBJECT, R.string.flickr_image);
            share.putExtra(Intent.EXTRA_TEXT, url);

            startActivity(Intent.createChooser(share, getString(R.string.share_with)));
        }
    }

    private void hideFAB(FloatingActionButton fab) {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
        FloatingActionButton.Behavior behavior = (FloatingActionButton.Behavior) params.getBehavior();
        if (behavior != null) behavior.setAutoHideEnabled(false);
        fab.hide();
    }

    private void showFAB(FloatingActionButton fab) {
        fab.show();
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
        FloatingActionButton.Behavior behavior = (FloatingActionButton.Behavior) params.getBehavior();
        if (behavior != null) behavior.setAutoHideEnabled(true);
    }
}
