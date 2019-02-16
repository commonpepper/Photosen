package com.commonpepper.photosen.ui.viewmodels;

import com.commonpepper.photosen.network.datasource.SearchListDataSourceFactory;
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
        return (T) new SearchListFragmentViewModel();
    }

    public class SearchListFragmentViewModel extends AbstractListFragmentViewModel<Photo> {

        @Override
        public SearchListDataSourceFactory createDataSourceFactory() {
            return new SearchListDataSourceFactory(query);
        }
    }

}
