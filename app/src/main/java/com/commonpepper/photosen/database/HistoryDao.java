package com.commonpepper.photosen.database;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.commonpepper.photosen.model.Photo;

@Dao
public interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Photo photo);

    @Query("SELECT * FROM photo ORDER BY time DESC")
    DataSource.Factory<Integer, Photo> getPaged();

    @Query("DELETE FROM photo")
    void clear();
}
