package com.example.xmlparsingmovies

import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    lateinit var rvReviews: RecyclerView
    private var allReviews = mutableListOf<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvReviews = findViewById(R.id.rvReviews)

        rvReviews.layoutManager = LinearLayoutManager(this)

        BringMovieReviews().execute()
    }
    private inner class BringMovieReviews : AsyncTask<Void, Void, MutableList<Movie>>(){
        val parser = XMLParser()
        override fun doInBackground(vararg p0: Void?): MutableList<Movie> {
            val url = URL("http://www.rediff.com/rss/moviesreviewsrss.xml")
            val urlConnection = url.openConnection() as HttpURLConnection
            allReviews =
                urlConnection.getInputStream()?.let {
                    parser.parse(it)
                }
                        as MutableList<Movie>
            return allReviews
        }
        override fun onPostExecute(result: MutableList<Movie>?) {
            super.onPostExecute(result)
            rvReviews.adapter = result?.let { RVreviews(it) }
            rvReviews.adapter?.notifyDataSetChanged()
            rvReviews.scrollToPosition(allReviews.size - 1)

        }
    }
}