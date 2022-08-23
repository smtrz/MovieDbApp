package com.zenjob.android.browsr.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zenjob.android.browsr.Constants.WebServiceConstants.Companion.POSTER_PATH
import com.zenjob.android.browsr.R
import com.zenjob.android.browsr.data.Movie
import com.zenjob.android.browsr.helpers.DateTimeHelpers
import kotlinx.android.synthetic.main.movie_item.view.*


class MoviesAdapterPaginated :
    PagingDataAdapter<Movie, RecyclerView.ViewHolder>(DataDifferntiator) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    private lateinit var itemClickListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.movie_item, parent, false)
        )
    }

    object DataDifferntiator : DiffUtil.ItemCallback<Movie>() {

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tvTitle.text = "${getItem(position)?.title} "
        holder.itemView.tvDesc.text = getItem(position)?.overview
        holder.itemView.ratingBar.rating = getItem(position)?.vote_average!!
        holder.itemView.tvRealeseDate.text =
            "Released " +
                    DateTimeHelpers.getDaysDifference(
                        DateTimeHelpers.getCurrentDate(),
                        getItem(position)?.release_date?.let { DateTimeHelpers.convertStringToDate(it) }
                    )
                        .toString() +
                    " days ago"
        Picasso.get().load(POSTER_PATH + getItem(position)?.poster_path).into(holder.itemView.imgPhoto)

        holder.itemView.setOnClickListener{
            itemClickListener.onItemClick(getItem(position)!!, position,it)
        }

    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
}

interface OnItemClickListener {
    fun onItemClick(listitem: Movie, position: Int,view: View?)
}


