package com.example.bookfilter2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.room.Database
import androidx.room.Room
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var ViewModell:AuthorsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val authorInput = findViewById<TextInputLayout>(R.id.AuthorInput)
        val country = findViewById<TextInputLayout>(R.id.Country)
        val dataCount = findViewById<TextView>(R.id.resultOne)
        val dataResultTwo = findViewById<TextView>(R.id.resulttwo)
        val dataResultThree = findViewById<TextView>(R.id.resultThree)
        val dataResultFour = findViewById<TextView>(R.id.resultFour)
        val filterButton = findViewById<Button>(R.id.button)
        val titles = mutableListOf<String>()
        val myApplication=application as MyApplication
        val authlist=myApplication.httpApiService
        ViewModell=ViewModelProvider(this).get(AuthorsViewModel::class.java)
        filterButton.setOnClickListener {
            titles.clear()
            dataCount.text = ""
            dataResultTwo.text = ""
            dataResultThree.text = ""
            try {
                CoroutineScope(Dispatchers.IO).launch {
                    var result = authlist.getMyBookData()
                    for (item in result) {
                        var a:Myauthors=Myauthors(author = item.author, country = item.country,title=item.title, imageLink = item.imageLink, link = item.link, pages = item.pages, language = item.language,year=item.year)
                        ViewModell.addUser(a)
                    }
                    val AuthursList:List<Myauthors> = ViewModell.ReadAllData
                    for(i in AuthursList){
                        if(i.author?.lowercase()==authorInput.editText?.text?.toString()?.lowercase() && i.country?.lowercase()==country.editText?.text?.toString()?.lowercase()){
                            titles.add(i.title.toString())
                        }
                    }
                    withContext(Dispatchers.Main) {
                        dataCount.setText("Result: ${titles.size}")
                        var res = " "
                        for (item in titles) {
                            res += "Result ${item} \n"
                        }
                        dataResultTwo.text = res
                    }
                }
            }
            catch(e:Exception){
dataResultFour.text="no results"
            }


        }
        }
}