package com.commonpepper.photosen.ui.viewmodels;

import com.commonpepper.photosen.network.datasource.SearchDataSourceFactory;
import com.commonpepper.photosen.network.model.Photo;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class SearchFragmentViewModelFactory implements ViewModelProvider.Factory {

    private String query;

    public SearchFragmentViewModelFactory(String query) {
        this.query = query;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SearchFragmentViewModelFactory.SearchFragmentViewModel();
    }

    public class SearchFragmentViewModel extends MyAbstractFragmentViewModel<Photo> {

        @Override
        public SearchDataSourceFactory createDataSourceFactory() {
            return new SearchDataSourceFactory(query);
        }
    }

}
