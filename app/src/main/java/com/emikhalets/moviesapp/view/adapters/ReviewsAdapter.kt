package com.emikhalets.moviesapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.emikhalets.moviesapp.R
import com.emikhalets.moviesapp.databinding.ItemReviewBinding
import com.emikhalets.moviesapp.model.pojo.ResultReview
import com.emikhalets.moviesapp.utils.buildProfile185px
import java.text.SimpleDateFormat
import java.util.*

class ReviewsAdapter(
    private val imageCornerRadius: Float,
    private val clickListener: (ResultReview) -> Unit
) : ListAdapter<ResultReview, ReviewsAdapter.ViewHolder>(ResultReviewDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.newInstance(parent, imageCornerRadius)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener { clickListener.invoke(getItem(position)) }
    }

    class ViewHolder(
        private val binding: ItemReviewBinding,
        private val imageCornerRadius: Float
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ResultReview) {
            with(binding) {
                imageAvatar.load(item.author_details.avatar_path?.let { buildProfile185px(it) }) {
                    crossfade(500)
                    placeholder(R.drawable.ph_avatar)
                    fallback(R.drawable.ph_avatar)
                    transformations(RoundedCornersTransformation(imageCornerRadius))
                }
                textName.text = item.author
                textRating.text = itemView.context.getString(
                    R.string.variable_rating,
                    item.author_details.rating
                )
                textUpdated.text = formatDate(item.updated_at)
                textContent.text = item.content
            }
        }

        private fun formatDate(dateStr: String): String {
            val formatIn = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.getDefault())
            val formatOut = SimpleDateFormat("dd-MMMM-yyyy", Locale.getDefault())
            val date = formatIn.parse(dateStr) ?: Date()
            return formatOut.format(date)
        }

        companion object {
            fun newInstance(parent: ViewGroup, imageCornerRadius: Float): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemReviewBinding.inflate(inflater, parent, false)
                return ViewHolder(binding, imageCornerRadius)
            }
        }
    }

    class ResultReviewDiffCallback : DiffUtil.ItemCallback<ResultReview>() {

        override fun areItemsTheSame(oldItem: ResultReview, newItem: ResultReview): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ResultReview, newItem: ResultReview): Boolean {
            return oldItem == newItem
        }
    }
}