package com.zenjob.android.browsr.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zenjob.android.browsr.Constants.WebServiceConstants.Companion.POSTER_PATH
import com.zenjob.android.browsr.R
import com.zenjob.android.browsr.data.Movie
import com.zenjob.android.browsr.generics.BaseRecyclerViewAdapter
import com.zenjob.android.browsr.helpers.DateTimeHelpers
import kotlinx.android.synthetic.main.movie_item.view.*

class MoviesAdapter() : BaseRecyclerViewAdapter<Movie>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val myHolder = holder as? MyViewHolder
        myHolder?.setUpView(getItem(position))
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private var model: Movie? = null

        init {
            view.setOnClickListener(this)
        }

        private val imgPhoto: ImageView = view.imgPhoto
        private val tvTitle: TextView = view.tvTitle
        private val tvRealeseDate: TextView = view.tvRealeseDate
        private val tvDesc: TextView = view.tvDesc
        private val ratingBar: RatingBar = view.ratingBar
        private val moviecard: FrameLayout = view.moviecard

        fun setUpView(movie: Movie?) {
            model = movie
            Picasso.get().load(POSTER_PATH + movie?.poster_path).into(imgPhoto)

            tvTitle.text = movie?.title
            tvDesc.text = movie?.overview
            tvRealeseDate.text =
                "Released " +
                        DateTimeHelpers.getDaysDifference(
                            DateTimeHelpers.getCurrentDate(),
                            movie?.release_date?.let { DateTimeHelpers.convertStringToDate(it) }
                        )
                            .toString() +
                        " days ago"
            ratingBar.rating = movie?.vote_average!!
            moviecard.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            model?.let { itemClickListener.onItemClick(it, absoluteAdapterPosition, v) }
        }
    }
}
