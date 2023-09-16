package com.severin.movies.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.severin.movies.databinding.GridRecyclerItemBinding
import com.severin.movies.data.model.MovieItemDB
import com.severin.movies.utils.ConstantSet

class MovieFromDbAdapter(
    private val clickListener: MovieFromDBAdapterClickListener
) : ListAdapter<MovieItemDB, MovieFromDbAdapter.MovieFromDbViewHolder>(MovieDiffCallBack()) {

    private class MovieDiffCallBack : DiffUtil.ItemCallback<MovieItemDB>() {
        override fun areItemsTheSame(oldItem: MovieItemDB, newItem: MovieItemDB): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MovieItemDB, newItem: MovieItemDB): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieFromDbViewHolder {
        return MovieFromDbViewHolder(
            GridRecyclerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieFromDbViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MovieFromDbViewHolder(
        private val itemBinding: GridRecyclerItemBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(movieItemDB: MovieItemDB) {
            itemBinding.tvItemName.text = movieItemDB.title

            val imageUrl = ConstantSet.API_IMAGE_BASE_URL_AND_FILE_SIZE + movieItemDB.poster_path
            Glide.with(itemBinding.root.context)
                .load(imageUrl)
                .into(itemBinding.ivItemPoster)

            itemBinding.root.setOnClickListener {
                clickListener.onClick(movieItemDB)
            }

            itemBinding.root.setOnLongClickListener {
                clickListener.onLongClick(movieItemDB)
                true
            }
        }
    }
}