package com.example.bookfilter2

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AuthorDao {
    @Query("Select * from myauthors")
    fun getAll():List<Myauthors>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(auth:Myauthors)
}