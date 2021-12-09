package com.example.bookfilter2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Database
import androidx.room.Room
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.*
import java.lang.Exception

class MainActivity : AppCompatActivity()
{
   // private lateinit var ViewModell:AuthorsViewModel
    //private lateinit var viewModel:BookViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val authorInput = findViewById<TextInputLayout>(R.id.AuthorInput)
        val dataCount = findViewById<TextView>(R.id.resultOne)
        val dataResultTwo = findViewById<TextView>(R.id.resulttwo)
        val filterButton = findViewById<Button>(R.id.button)
        val titles = mutableListOf<Bookdata>()
        val myApplication=application as MyApplication
        val authlist=myApplication.httpApiService
        filterButton.setOnClickListener {
            titles.clear()
            dataCount.text = ""
            dataResultTwo.text = ""
            var c: Int
            dataResultTwo.text=""
                CoroutineScope(Dispatchers.IO).launch {

                    var result = authlist.getMyBookData()
                    for (i in result)
                        titles.add(i)
                }
                GlobalScope.launch {
                    var auth:Int=0
                        for (item in titles) {
                            titles.add(item)
                           c = 0
                            val AuthursList: List<AuthorDetails> = AppDatabase.getDatabase(this@MainActivity).authorDao().getAll()
                                //var aa: AuthorDetails = AuthorDetails(author = item.author, country = item.country)
                                for (items in AuthursList) {
                                    if (items.author.lowercase() == item.author.lowercase()) {
                                        auth = items.Aid
                                        c = 1
                                        break
                                    }
                                }
                                if (c == 1) {
                                    AppDatabase.getDatabase(this@MainActivity).BookDao()
                                        .InsertBooks(
                                            BookDetail(
                                                aid = auth,
                                                language = item.language,
                                                imageLink = item.imageLink,
                                                link = item.link,
                                                pages = item.pages,
                                                title = item.title,
                                                year = item.year
                                            )
                                        )
                                } else {
                                    AppDatabase.getDatabase(this@MainActivity).authorDao().insert(
                                        AuthorDetails(
                                            author = item.author,
                                            country = item.country
                                        )
                                    )

                                    auth = AppDatabase.getDatabase(this@MainActivity).authorDao()
                                        .getAuhtor(item.author).Aid
                                    AppDatabase.getDatabase(this@MainActivity).BookDao()
                                        .InsertBooks(
                                            BookDetail(
                                                aid = auth,
                                                language = item.language,
                                                imageLink = item.imageLink,
                                                link = item.link,
                                                pages = item.pages,
                                                title = item.title,
                                                year = item.year
                                            )
                                        )
                            }
                        }
                    val list:List<Authorandbook> = AppDatabase.getDatabase(this@MainActivity).authorDao().JoinedDetails(authorInput.editText?.text?.toString()?.lowercase())
                    withContext(Dispatchers.Main) {

                        dataCount.setText("Result: ${list.size}")
                        var res = ""
                        for (item in list) {
                            res += "Result: ID: ${item.BookID} Title: ${item.title} \n"
                        }
                        dataResultTwo.text = res
                    }
                }
                }

        }
}
