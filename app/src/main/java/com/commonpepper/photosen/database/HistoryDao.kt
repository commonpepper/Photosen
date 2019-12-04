package com.commonpepper.photosen.database

import androidx.paging.DataSource.Factory
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.commonpepper.photosen.model.Photo

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(photo: Photo)

    @get:Query("SELECT * FROM photo ORDER BY time DESC")
    val paged: Factory<Int, Photo>

    @Query("DELETE FROM photo")
    fun clear()
}
