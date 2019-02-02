package com.commonpepper.photosen.ui.viewmodels;

import com.commonpepper.photosen.network.datasource.PhotoDataSourceFactory;
import com.commonpepper.photosen.network.model.Photo;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class PhotoFragmentViewModelFactory implements ViewModelProvider.Factory {

    private String order_by;

    public PhotoFragmentViewModelFactory(String order_by) {
        this.order_by = order_by;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PhotoFragmentViewModel();
    }

    public class PhotoFragmentViewModel extends MyAbstractFragmentViewModel<Photo> {

        @Override
        public PhotoDataSourceFactory createDataSourceFactory() {
            return new PhotoDataSourceFactory(order_by);
        }
    }

}
