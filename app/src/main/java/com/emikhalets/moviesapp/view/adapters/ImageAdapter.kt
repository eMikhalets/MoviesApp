package com.emikhalets.moviesapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.emikhalets.moviesapp.R
import com.emikhalets.moviesapp.databinding.ItemImageBinding
import com.emikhalets.moviesapp.model.pojo.Profile
import com.emikhalets.moviesapp.utils.buildProfileUrl185px
import com.emikhalets.moviesapp.view.adapters.ImageAdapter.ViewHolder

class ImageAdapter(
    private val clickListener: (String) -> Unit
) : ListAdapter<Profile, ViewHolder>(ImageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.newInstance(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener { clickListener.invoke(getItem(position).file_path) }
    }

    class ViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Profile) {
            with(binding) {
                imageAvatar.load(buildProfileUrl185px(item.file_path)) {
                    placeholder(R.drawable.ph_avatar)
                    fallback(R.drawable.ph_avatar)
                }
            }
        }

        companion object {
            fun newInstance(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemImageBinding.inflate(inflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class ImageDiffCallback : DiffUtil.ItemCallback<Profile>() {

        override fun areItemsTheSame(oldItem: Profile, newItem: Profile): Boolean {
            return oldItem.file_path == newItem.file_path
        }

        override fun areContentsTheSame(oldItem: Profile, newItem: Profile): Boolean {
            return oldItem == newItem
        }
    }
}