package com.commonpepper.photosen.database

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.commonpepper.photosen.model.Photo

@Dao
interface HistoryDao {

    @get:Query("SELECT * FROM photo ORDER BY time DESC")
    val paged: DataSource.Factory<Int, Photo>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(photo: Photo)

    @Query("DELETE FROM photo")
    fun clear()
}
