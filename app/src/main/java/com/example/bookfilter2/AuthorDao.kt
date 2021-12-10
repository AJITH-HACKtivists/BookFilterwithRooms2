package com.example.bookfilter2

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AuthorDao {
    @Query("Select * from authordetails")
    suspend fun getAll():List<AuthorDetails>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(auth:AuthorDetails)
    @Query("Select * from authordetails where author=:name")
    suspend fun getAuhtor(name:String):AuthorDetails
    @Query("Select * from AUTHORDETAILS a Join BooksDetail b on a.Aid=b.aid where Lower(a.author)=:name")
    suspend fun JoinedDetails(name:String?):List<Authorandbook>
}