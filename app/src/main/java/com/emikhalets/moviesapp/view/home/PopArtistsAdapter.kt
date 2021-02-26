package com.emikhalets.moviesapp.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.emikhalets.moviesapp.R
import com.emikhalets.moviesapp.databinding.ItemPopArtistBinding
import com.emikhalets.moviesapp.model.pojo.PopArtistsResult
import com.emikhalets.moviesapp.utils.buildProfileUrl185px
import com.emikhalets.moviesapp.view.home.PopArtistsAdapter.ViewHolder

class PopArtistsAdapter(private val imageCornerRadius: Float) :
    ListAdapter<PopArtistsResult, ViewHolder>(PopArtistsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.newInstance(parent, imageCornerRadius)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemPopArtistBinding,
        private val imageCornerRadius: Float
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PopArtistsResult) {
            with(binding) {
                imagePhoto.load(buildProfileUrl185px(item.profile_path)) {
                    crossfade(500)
                    placeholder(R.drawable.ph_actor)
                    fallback(R.drawable.ph_actor)
                    transformations(RoundedCornersTransformation(imageCornerRadius))
                }
                textName.text = item.name
            }
        }

        companion object {
            fun newInstance(parent: ViewGroup, imageCornerRadius: Float): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemPopArtistBinding.inflate(inflater, parent, false)
                return ViewHolder(binding, imageCornerRadius)
            }
        }
    }

    class PopArtistsDiffCallback : DiffUtil.ItemCallback<PopArtistsResult>() {

        override fun areItemsTheSame(
            oldItem: PopArtistsResult,
            newItem: PopArtistsResult
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PopArtistsResult,
            newItem: PopArtistsResult
        ): Boolean {
            return oldItem == newItem
        }
    }
}