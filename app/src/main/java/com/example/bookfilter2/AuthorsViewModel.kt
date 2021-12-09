package com.example.bookfilter2

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class AuthorsViewModel(application: Application):AndroidViewModel(application) {
    val ReadAllData:List<Myauthors>
    private val repo:AuthorRepo
    init{
        val authorDao=AppDatabase.getDatabase(application).authorDao()
        ReadAllData=authorDao.getAll()
        repo= AuthorRepo(authorDao)
    }
    fun addUser(author:Myauthors){
        CoroutineScope(Dispatchers.IO).launch {
            repo.Insert(author)
        }
    }
}