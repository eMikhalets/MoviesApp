package com.emikhalets.moviesapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.emikhalets.moviesapp.R
import com.emikhalets.moviesapp.databinding.ItemCastBinding
import com.emikhalets.moviesapp.model.pojo.ResultPopArtist
import com.emikhalets.moviesapp.utils.buildProfile185px
import com.emikhalets.moviesapp.view.adapters.PopPersonListAdapter.ViewHolder

class PopPersonListAdapter(
    private val imageCornerRadius: Float,
    private val clickListener: (Int) -> Unit
) : PagedListAdapter<ResultPopArtist, ViewHolder>(PopArtistDiffCallback()) {

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
        private val binding: ItemCastBinding,
        private val imageCornerRadius: Float
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ResultPopArtist) {
            with(binding) {
                imagePhoto.load(buildProfile185px(item.profile_path)) {
                    placeholder(R.drawable.ph_actor)
                    fallback(R.drawable.ph_actor)
                    transformations(RoundedCornersTransformation(imageCornerRadius))
                }
                textName.text = item.name
                textBottom.text = getGender(item.gender)
            }
        }

        private fun getGender(intGender: Int): String {
            return when (intGender) {
                1 -> "Female"
                2 -> "Male"
                else -> "-"
            }
        }

        companion object {
            fun newInstance(parent: ViewGroup, imageCornerRadius: Float): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemCastBinding.inflate(inflater, parent, false)
                return ViewHolder(binding, imageCornerRadius)
            }
        }
    }

    class PopArtistDiffCallback : DiffUtil.ItemCallback<ResultPopArtist>() {

        override fun areItemsTheSame(oldItem: ResultPopArtist, newItem: ResultPopArtist): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ResultPopArtist,
            newItem: ResultPopArtist
        ): Boolean {
            return oldItem == newItem
        }
    }
}