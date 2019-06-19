package com.commonpepper.photosen.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.commonpepper.photosen.Photosen;
import com.commonpepper.photosen.model.Photo;

import java.util.concurrent.Executors;

public class HistoryActivityViewModel extends ViewModel {

    private DataSource.Factory<Integer, Photo> dataSourceFactory;
    private LiveData<PagedList<Photo>> photosList;

    public HistoryActivityViewModel() {
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(Photosen.PAGE_SIZE)
                .setPrefetchDistance(Photosen.PREFETCH_DISTANCE)
                .setInitialLoadSizeHint(Photosen.PAGE_SIZE)
                .build();

        dataSourceFactory = Photosen.getInstance().getDatabase().getHistoryDao().getPaged();

        photosList = new LivePagedListBuilder<>(dataSourceFactory, config)
                .setFetchExecutor(Executors.newSingleThreadExecutor())
                .build();
    }

    public LiveData<PagedList<Photo>> getPhotosList() {
        return photosList;
    }
}
