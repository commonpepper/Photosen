package com.commonpepper.photosen.ui.viewmodels;

import java.util.HashSet;
import java.util.Set;

import androidx.lifecycle.ViewModel;

public class SearchActivityViewModel extends ViewModel {
    private Set<String> tags = new HashSet<>();
    private String queue;

    public SearchActivityViewModel() {
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getQueue() {
        return queue;
    }

    public Set<String> getTags() {
        return tags;
    }

    public String tagsToString() {
        Set<String> set = tags;
        if (set.size() == 0) return null;
        StringBuilder ans = new StringBuilder();
        for (String tag : set) {
            ans.append(tag);
            ans.append(',');
        }
        return ans.toString().substring(0, ans.length() - 1);
    }
}
