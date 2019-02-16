package com.commonpepper.photosen.ui.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.commonpepper.photosen.Photosen;
import com.commonpepper.photosen.R;
import com.commonpepper.photosen.network.DownloadService;
import com.commonpepper.photosen.network.model.Photo;
import com.commonpepper.photosen.ui.fragments.PhotoDetailsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SinglePhotoActivity extends AppCompatActivity {
    private static final String TAG = SinglePhotoActivity.class.getSimpleName();

    public static final String PHOTO_TAG = "PARCELABLE_PHOTO";
    public static final String PHOTOS_URL = "https://www.flickr.com/photos/";

    private Photo photo;

    private DownloadService.Aciton action;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_photo);

        Toolbar toolbar = findViewById(R.id.single_image_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        photo = getIntent().getParcelableExtra(PHOTO_TAG);

        TextView title = findViewById(R.id.single_photo_title);
        ImageView imageView = findViewById(R.id.single_photo_image_view);

        FloatingActionButton fabDownload = findViewById(R.id.fab_download);
        FloatingActionButton fabSetWallpaper = findViewById(R.id.fab_wallpaper);

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

        if (getSupportFragmentManager().getFragments().size() == 0) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.single_image_details_layout, PhotoDetailsFragment.newInstance(photo))
                    .commit();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void openInBrowser(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
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
        }

        return super.onOptionsItemSelected(item);
    }

    private void downloadPhoto() {
        if (!DownloadService.isRunning) {
            Toast.makeText(this, getString(R.string.download_started), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, DownloadService.class);
            intent.putExtra(DownloadService.TAG_URL, photo.getUrl_o());
            intent.putExtra(DownloadService.TAG_FILENAME, photo.getId() + "." + photo.getOriginalformat());
            intent.putExtra(DownloadService.TAG_ACTION, action);
            startService(intent);
        } else {
            Toast.makeText(this, getString(R.string.please_wait_for_current_download), Toast.LENGTH_SHORT).show();
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
            if (action != null) downloadPhoto();
        } else {
            Toast.makeText(this, getString(R.string.no_permisson), Toast.LENGTH_SHORT).show();
        }
    }
}
