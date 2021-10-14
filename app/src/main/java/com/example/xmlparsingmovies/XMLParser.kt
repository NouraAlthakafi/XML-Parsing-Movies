package com.example.xmlparsingmovies

import android.util.Xml
import org.xmlpull.v1.XmlPullParser
import java.io.InputStream

class XMLParser {
    private val mr: String? = null

    fun parse(inputStream: InputStream): ArrayList<Movie> {
        inputStream.use { inputStream ->
            val parser: XmlPullParser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(inputStream, null)
            parser.nextTag()
            return readMovieReviewsRssFeed(parser)
        }
    }

    private fun readMovieReviewsRssFeed(parser: XmlPullParser): ArrayList<Movie> {
        val movieReview = ArrayList<Movie>()

        parser.require(XmlPullParser.START_TAG, mr, "rss")

        while (parser.next() != XmlPullParser.END_TAG) {

            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            if (parser.name == "channel") {
                parser.require(XmlPullParser.START_TAG, mr, "channel")
                var title: String? = null
                var link: String? = null
                var description: String? = null
                while (parser.next() != XmlPullParser.END_TAG) {
                    if (parser.eventType != XmlPullParser.START_TAG) {
                        continue
                    }
                    when (parser.name) {
                        "title" -> title = readTitle(parser)
                        "link" -> link = readLink(parser)
                        "description" -> description = readDescription(parser)
                        else -> skip(parser)
                    }
                }
                movieReview.add(Movie(title, link, description))
            } else {
                skip(parser)
            }
        }
        return movieReview
    }

    private fun readTitle(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, mr, "title")
        val title = readText(parser)
        parser.require(XmlPullParser.END_TAG, mr, "title")
        return title
    }

    private fun readLink(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, mr, "link")
        val link = readText(parser)
        parser.require(XmlPullParser.END_TAG, mr, "link")
        return link
    }

    private fun readDescription(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, mr, "description")
        val description = readText(parser)
        parser.require(XmlPullParser.END_TAG, mr, "description")
        return description
    }

    private fun readText(parser: XmlPullParser): String {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        return result
    }

    private fun skip(parser: XmlPullParser) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            throw IllegalStateException()
        }
        var deep = 1
        while (deep != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> deep--
                XmlPullParser.START_TAG -> deep++
            }
        }
    }
}