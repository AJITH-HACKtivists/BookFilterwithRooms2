package com.example.bookfilter2

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "BooksDetail",  indices = arrayOf(Index(value = ["title"],unique=true)), foreignKeys = [ForeignKey(entity = AuthorDetails::class,parentColumns = arrayOf("Aid"), childColumns = arrayOf("aid"), onDelete = ForeignKey.CASCADE)])
data class BookDetail(
    @PrimaryKey(autoGenerate = true) var BookID:Int=0,
    var aid:Int,
    var imageLink: String,
    var language: String,
    var link:String,
    var pages: Int,
    var title: String,
    var year: Int,
)
