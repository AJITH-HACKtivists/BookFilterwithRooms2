package com.example.bookfilter2

import android.app.Application
import androidx.lifecycle.LiveData

class AuthorRepo(private val authorDao: AuthorDao) {
    val readAllData:List<Myauthors> = authorDao.getAll()
    suspend fun Insert(authors:Myauthors){
        authorDao.insert(authors)
    }
}