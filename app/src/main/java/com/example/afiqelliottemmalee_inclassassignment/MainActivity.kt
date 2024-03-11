package com.example.afiqelliottemmalee_inclassassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchbar = findViewById<EditText>(R.id.editText)
        val searchButton = findViewById<Button>(R.id.button)
        val moviePoster = findViewById<ImageView>(R.id.posterImage)
        val movieTitle = findViewById<TextView>(R.id.movieTitle)
        val movieYear = findViewById<TextView>(R.id.movieYear)

        searchButton.setOnClickListener{
            val title = searchbar.text.toString()
            movieSearch(title, searchbar, searchButton, moviePoster, movieTitle, movieYear)
        }
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.omdbapi.com/")
        .addConverterFactory(MoshiConverterFactory.create(
            Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        ))
        .build()

    private val movieAPI = retrofit.create(MovieAPI::class.java)

    @OptIn(DelicateCoroutinesApi::class)
    private fun movieSearch(title: String, searchbar: EditText, searchButton: Button, poster: ImageView,titleText: TextView, yearText: TextView) {
        val titleSearch = title.replace(" ", "+")
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val movieReturned = movieAPI.searchMovies(titleSearch)
                print(movieReturned)
                poster.load(movieReturned.poster)
                titleText.text = movieReturned.title
                yearText.text = movieReturned.year
                poster.visibility = View.VISIBLE
                titleText.visibility = View.VISIBLE
                yearText.visibility = View.VISIBLE

            } catch (e: Exception) {
                println(e.message)
            }
        }
    }
}