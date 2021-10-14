package com.example.xmlparsingmovies

data class Movie(val title: String?, val link: String?, val description: String?){
    override fun toString(): String = title!!
}