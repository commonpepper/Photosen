package com.commonpepper.photosen.ui.viewmodels;

import com.commonpepper.photosen.network.datasource.PhotoListDataSourceFactory;
import com.commonpepper.photosen.model.Photo;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class PhotoFragmentViewModelFactory implements ViewModelProvider.Factory {

    private String date;

    public PhotoFragmentViewModelFactory(String date) {
        this.date = date;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PhotoListFragmentViewModel();
    }

    public class PhotoListFragmentViewModel extends AbstractListFragmentViewModel<Photo> {

        @Override
        public PhotoListDataSourceFactory createDataSourceFactory() {
            return new PhotoListDataSourceFactory(date);
        }
    }

}
