package com.commonpepper.photosen.ui.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.commonpepper.photosen.network.datasource.UserPhotosListDataSourceFactory;
import com.commonpepper.photosen.model.Photo;

public class UserPhotosFragmentViewModelFactory implements ViewModelProvider.Factory {

    private String user_id;

    public UserPhotosFragmentViewModelFactory(String user_id) {
        this.user_id = user_id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new UserPhotosListFragmentViewModel();
    }

    public class UserPhotosListFragmentViewModel extends AbstractListFragmentViewModel<Photo> {

        @Override
        public UserPhotosListDataSourceFactory createDataSourceFactory() {
            return new UserPhotosListDataSourceFactory(user_id);
        }
    }

}
