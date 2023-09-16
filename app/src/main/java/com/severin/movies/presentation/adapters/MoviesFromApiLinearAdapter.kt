package com.severin.movies.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.severin.movies.databinding.LinearRecyclerItemBinding
import com.severin.movies.data.model.MovieItemApi
import com.severin.movies.utils.ConstantSet

class MoviesFromApiLinearAdapter(
    private val clickListener: MovieFromApiAdapterClickListener
) : ListAdapter<MovieItemApi, MoviesFromApiLinearAdapter.MovieFromApiViewHolder>(MovieDiffCallBack()) {

    private class MovieDiffCallBack : DiffUtil.ItemCallback<MovieItemApi>() {
        override fun areItemsTheSame(oldItem: MovieItemApi, newItem: MovieItemApi): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MovieItemApi, newItem: MovieItemApi): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieFromApiViewHolder {
        return MovieFromApiViewHolder(
            LinearRecyclerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieFromApiViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MovieFromApiViewHolder(
        private val itemBinding: LinearRecyclerItemBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(movieItemApi: MovieItemApi) {
            itemBinding.tvItemName.text = movieItemApi.title

            val imageUrl = ConstantSet.API_IMAGE_BASE_URL_AND_FILE_SIZE + movieItemApi.poster_path
            Glide.with(itemBinding.root.context)
                .load(imageUrl)
                .into(itemBinding.ivItemPoster)

            itemBinding.root.setOnClickListener {
                clickListener.onClick(movieItemApi)
            }

            itemBinding.root.setOnLongClickListener {
                clickListener.onLongClick(movieItemApi)
                true
            }
        }
    }
}