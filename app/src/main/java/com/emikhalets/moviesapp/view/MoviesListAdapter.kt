package com.emikhalets.moviesapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.emikhalets.moviesapp.R
import com.emikhalets.moviesapp.databinding.ItemMovieBinding
import com.emikhalets.moviesapp.model.pojo.MovieListResult
import com.emikhalets.moviesapp.utils.buildPosterUrl185px
import com.emikhalets.moviesapp.view.MoviesListAdapter.ViewHolder

class MoviesListAdapter : ListAdapter<MovieListResult, ViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.newInstance(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemMovieBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MovieListResult) {
            with(binding) {
                imagePoster.load(buildPosterUrl185px(item.poster_path)) {
                    crossfade(500)
                    placeholder(R.drawable.ph_actor)
                    fallback(R.drawable.ph_actor)
                    transformations(RoundedCornersTransformation(8f))
                }
                textTitle.text = item.title
                textBottom.text = root.context.getString(
                        R.string.item_movie_text_bottom,
                        item.vote_average,
                        parseYear(item.release_date))
            }
        }

        private fun parseYear(release: String): String {
            return try {
                release.split("-").first()
            } catch (indexEx: IndexOutOfBoundsException) {
                indexEx.printStackTrace()
                "No year"
            }
        }

        companion object {
            fun newInstance(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemMovieBinding.inflate(inflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<MovieListResult>() {

        override fun areItemsTheSame(oldItem: MovieListResult, newItem: MovieListResult): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieListResult, newItem: MovieListResult): Boolean {
            return oldItem == newItem
        }
    }
}