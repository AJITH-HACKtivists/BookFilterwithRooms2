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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val authorInput = findViewById<TextInputLayout>(R.id.AuthorInput)
        val dataCount = findViewById<TextView>(R.id.resultOne)
        val dataResultTwo = findViewById<TextView>(R.id.resulttwo)
        val filterButton = findViewById<Button>(R.id.button)
        val titles = mutableListOf<Bookdata>()
        val myApplication = application as MyApplication
        val authlist = myApplication.httpApiService
        CoroutineScope(Dispatchers.IO).launch {
            var result = authlist.getMyBookData()
            for (i in result)
                titles.add(i)
            var auth: Int = 0
            for (item in titles) {

                AppDatabase.getDatabase(this@MainActivity).authorDao().insert(AuthorDetails(author = item.author, country = item.country))
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
        filterButton.setOnClickListener {
            titles.clear()
            dataCount.text = ""
            dataResultTwo.text = ""
            var c: Int
            dataResultTwo.text = ""
            CoroutineScope(Dispatchers.IO).launch {
                val list: List<Authorandbook> =
                    AppDatabase.getDatabase(this@MainActivity).authorDao()
                        .JoinedDetails(authorInput.editText?.text?.toString()?.lowercase())
                withContext(Dispatchers.Main) {

                    var count: Int = 0
                    var res = ""

                    if (list.size >= 1) {
                        res += "Result: ${list[0].title} (${list[0].BookID})\n"
                        count += 1
                    }
                    if (list.size >= 2) {
                        res += "Result: ${list[1].title} (${list[1].BookID})\n"
                        count += 1
                    }
                    if (list.size >= 3) {
                        res += "Result: ${list[2].title} (${list[2].BookID})\n"
                        count += 1
                    }
                    dataCount.text = "Result: $count"
                    dataResultTwo.text = res
                }
            }

        }

    }
}
