package com.severin.movies.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.severin.movies.databinding.GenreRecyclerItemBinding
import com.severin.movies.data.model.Genre
import com.severin.movies.utils.ConstantSet

class GenresAdapter(
    private val clickListener: GenresAdapterClickListener
) : ListAdapter<Genre, GenresAdapter.GenreViewHolder>(GenreDiffCallBack()) {

    private class GenreDiffCallBack : DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        return GenreViewHolder(
            GenreRecyclerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class GenreViewHolder(
        private val itemBinding: GenreRecyclerItemBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(genre: Genre) {
            itemBinding.tvItemName.text = genre.name

            val imageUrl =
                ConstantSet.API_IMAGE_BASE_URL_AND_FILE_SIZE + "/mDfJG3LC3Dqb67AZ52x3Z0jU0uB.jpg"//TODO(replace the hard coded image address into the next algorithm:
            //find three the most popular movies of the specified genre,
            //use their posters to insert the custom GenreRecyclerItemBinding in its image Views)
            Glide.with(itemBinding.root.context)
                .load(imageUrl)
                .into(itemBinding.ivItemPoster)

            itemBinding.root.setOnClickListener {
                clickListener.onClick(genre)
            }
        }
    }

}