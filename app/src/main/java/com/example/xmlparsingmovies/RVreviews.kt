package com.example.xmlparsingmovies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_reviews.view.*

class RVreviews(var allReviews: MutableList<com.example.xmlparsingmovies.Movie>): RecyclerView.Adapter<RVreviews.ItemViewHolder>() {
    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_reviews, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val another = allReviews[position]
        holder.itemView.apply{
            tvMovie.text = another.toString()
        }
    }

    override fun getItemCount()= allReviews.size
}