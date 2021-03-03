package com.emikhalets.moviesapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.emikhalets.moviesapp.R
import com.emikhalets.moviesapp.databinding.ItemPersonHomeBinding
import com.emikhalets.moviesapp.model.pojo.ResultPopArtist
import com.emikhalets.moviesapp.utils.buildProfileUrl185px
import com.emikhalets.moviesapp.view.adapters.HomePersonAdapter.ViewHolder

class HomePersonAdapter(
        private val imageCornerRadius: Float,
        private val clickListener: (Int) -> Unit
) : ListAdapter<ResultPopArtist, ViewHolder>(PopArtistsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.newInstance(parent, imageCornerRadius)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener { clickListener.invoke(getItem(position).id) }
    }

    class ViewHolder(
            private val binding: ItemPersonHomeBinding,
            private val imageCornerRadius: Float
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ResultPopArtist) {
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
                val binding = ItemPersonHomeBinding.inflate(inflater, parent, false)
                return ViewHolder(binding, imageCornerRadius)
            }
        }
    }

    class PopArtistsDiffCallback : DiffUtil.ItemCallback<ResultPopArtist>() {

        override fun areItemsTheSame(
                oldItem: ResultPopArtist,
                newItem: ResultPopArtist
        ): Boolean {
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