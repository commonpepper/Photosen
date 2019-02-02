package com.commonpepper.photosen.ui.activities;

import android.app.DownloadManager;
import android.app.IntentService;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SinglePhotoActivity extends AppCompatActivity {
    private static final String TAG = SinglePhotoActivity.class.getSimpleName();

    public static final String PHOTO_TAG = "PARCELABLE_PHOTO";

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

        TextView description = findViewById(R.id.single_photo_description);
        ImageView imageView = findViewById(R.id.single_photo_image_view);
        TextView username = findViewById(R.id.single_image_username);
        TextView likesNumber = findViewById(R.id.single_image_likes_number);
        ImageView imageViewAvatar = findViewById(R.id.single_photo_avatar_image_view);
        TextView location = findViewById(R.id.single_image_location);
        LinearLayout locationLayout = findViewById(R.id.single_image_location_layout);
        TextView textViewWidth = findViewById(R.id.single_image_width);
        TextView textViewHeigth = findViewById(R.id.single_image_height);
        TextView textViewDate = findViewById(R.id.single_image_date);
        LinearLayout dateLayout = findViewById(R.id.single_image_date_layout);
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

        if (photo.getDescription() != null && photo.getDescription().length() > 0) {
            description.setText(photo.getDescription());
        } else {
            description.setVisibility(View.GONE);
        }
        Picasso.get().load(photo.getUrls().getSmall()).into(imageView);
        Picasso.get().load(photo.getUser().getProfile_image().getLarge()).into(imageViewAvatar);
        if (photo.getUser().getUsername() != null) {
            SpannableString usernameStr = new SpannableString(photo.getUser().getUsername());
            usernameStr.setSpan(new UnderlineSpan(), 0, usernameStr.length(), 0);
            username.setText(photo.getUser().getUsername());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                username.setTextColor(getColor(R.color.colorAccent));
            }
            username.setOnClickListener(v -> openInBrowser(photo.getUser().getLinks().getHtml()));
        }
        likesNumber.setText(String.valueOf(photo.getLikes()));
        textViewWidth.setText(getResources().getQuantityString(R.plurals.x_pixels, photo.getWidth(), photo.getWidth()));
        textViewHeigth.setText(getResources().getQuantityString(R.plurals.x_pixels, photo.getHeight(), photo.getHeight()));
        if (photo.getUser().getLocation() != null && photo.getUser().getLocation().length() > 0) {
            location.setText(photo.getUser().getLocation());
        } else {
            locationLayout.setVisibility(View.GONE);
        }
        if (photo.getUpdated_at().length() > 0) {
            textViewDate.setText(photo.getUpdated_at().split("T")[0]);
        } else {
            dateLayout.setVisibility(View.GONE);
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
            openInBrowser(photo.getLinks().getHtml());
        }

        return super.onOptionsItemSelected(item);
    }

    private void downloadPhoto() {
        if (!DownloadService.isRunning) {
            Toast.makeText(this, getString(R.string.download_started), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, DownloadService.class);
            intent.putExtra(DownloadService.TAG_URL, photo.getUrls().getFull());
            intent.putExtra(DownloadService.TAG_FILENAME, photo.getId() + ".jpg");
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
