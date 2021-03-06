package com.emikhalets.moviesapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.emikhalets.moviesapp.R
import com.emikhalets.moviesapp.databinding.ItemMoviePagerBinding
import com.emikhalets.moviesapp.model.pojo.ResultMovieList
import com.emikhalets.moviesapp.utils.buildPoster185px
import com.emikhalets.moviesapp.view.adapters.MoviesPagerAdapter.ViewHolder

class MoviesPagerAdapter(
        private val imageCornerRadius: Float,
        private val clickListener: (Int) -> Unit
) : PagedListAdapter<ResultMovieList, ViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.newInstance(parent, imageCornerRadius)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
        holder.itemView.setOnClickListener {
            getItem(position)?.id?.let { id ->
                clickListener.invoke(id)
            }
        }
    }

    class ViewHolder(
            private val binding: ItemMoviePagerBinding,
            private val imageCornerRadius: Float
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ResultMovieList) {
            with(binding) {
                imagePoster.load(item.poster_path?.let { buildPoster185px(it) }) {
                    crossfade(500)
                    placeholder(R.drawable.ph_actor)
                    fallback(R.drawable.ph_actor)
                    transformations(RoundedCornersTransformation(imageCornerRadius))
                }
                textTitle.text = item.title
                textDate.text = item.release_date?.let { parseYear(it) }
                textRating.text = root.context.getString(
                        R.string.variable_rating,
                        item.vote_average.toString()
                )
                ratingBar.rating = item.vote_average.toFloat() / 2
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
            fun newInstance(parent: ViewGroup, imageCornerRadius: Float): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemMoviePagerBinding.inflate(inflater, parent, false)
                return ViewHolder(binding, imageCornerRadius)
            }
        }
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<ResultMovieList>() {

        override fun areItemsTheSame(oldItem: ResultMovieList, newItem: ResultMovieList): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
                oldItem: ResultMovieList,
                newItem: ResultMovieList
        ): Boolean {
            return oldItem == newItem
        }
    }
}