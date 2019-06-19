package com.commonpepper.photosen.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.commonpepper.photosen.model.Photo;

@Database(entities = {Photo.class}, version = 1)
public abstract class PhotosenDatabase extends RoomDatabase {
    public abstract HistoryDao getHistoryDao();
}
