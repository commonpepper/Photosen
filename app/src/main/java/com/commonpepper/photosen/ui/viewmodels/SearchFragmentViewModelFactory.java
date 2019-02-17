package com.commonpepper.photosen.ui.viewmodels;

import com.commonpepper.photosen.network.datasource.SearchListDataSourceFactory;
import com.commonpepper.photosen.network.model.Photo;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class SearchFragmentViewModelFactory implements ViewModelProvider.Factory {

    private String query;
    private String tags;

    public SearchFragmentViewModelFactory(String query, String tags) {
        this.query = query;
        this.tags = tags;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SearchListFragmentViewModel();
    }

    public class SearchListFragmentViewModel extends AbstractListFragmentViewModel<Photo> {

        @Override
        public SearchListDataSourceFactory createDataSourceFactory() {
            return new SearchListDataSourceFactory(query, tags);
        }
    }

}
