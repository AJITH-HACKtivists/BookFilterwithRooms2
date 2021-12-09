package com.example.bookfilter2

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "authordetails")
data class AuthorDetails(
    @PrimaryKey(autoGenerate = true) val Aid:Int=0,
    var author:String,
    var country:String,
    )

