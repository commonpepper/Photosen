package com.commonpepper.photosen.ui.activities;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.commonpepper.photosen.R;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CropActivity extends AppCompatActivity {
    public static final String TAG_URISTR = "tag_uri_str";
    private CropImageView cropView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        Toolbar toolbar = findViewById(R.id.toolbar);
        cropView = findViewById(R.id.cropView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        getSupportActionBar().setTitle(getString(R.string.crop_wallpaper));

        int width = WallpaperManager.getInstance(this).getDesiredMinimumWidth();
        int height = WallpaperManager.getInstance(this).getDesiredMinimumHeight();
//        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
//                .getPath() + File.separator + "wallpaper.jpg";
//        wallpaperUri = FileProvider.getUriForFile(this, Photosen.PACKAGE_NAME + ".fileprovider", new File(path));

        String uriStr = getIntent().getStringExtra(TAG_URISTR);
        Uri sourceUri = Uri.parse(uriStr);
        cropView.setImageUriAsync(sourceUri);
        cropView.setAspectRatio(width, height);

        cropView.setOnCropImageCompleteListener((view, result) -> {
            if (result.getError() == null) {
                try {
                    Bitmap bitmap = result.getBitmap();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        WallpaperManager.getInstance(this).setBitmap(bitmap, null, true,
                                WallpaperManager.FLAG_SYSTEM | WallpaperManager.FLAG_LOCK);
                    } else {
                        WallpaperManager.getInstance(this).setBitmap(bitmap);
                    }
                    finish();
                } catch (IOException e) {
                    showError(e);
                }
            } else {
                showError(result.getError());
            }
        });
    }

    private void showError(Exception e) {
        Toast.makeText(CropActivity.this, getString(R.string.error) + ": " + e.toString(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.crop_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.crop_done) {
            cropView.getCroppedImageAsync();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
