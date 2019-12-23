package com.commonpepper.photosen.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.commonpepper.photosen.model.Photo

@Database(entities = [Photo::class], version = 2)
abstract class PhotosenDatabase : RoomDatabase() {
    abstract val historyDao: HistoryDao
}
