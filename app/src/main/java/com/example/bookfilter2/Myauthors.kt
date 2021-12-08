package com.example.bookfilter2
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "myauthors", primaryKeys = ["author","title"])
data class Myauthors(
var author:String,
var country:String,
var imageLink: String,
var language: String,
var link:String,
var pages: Int,
var title: String,
var year: Int,
)

