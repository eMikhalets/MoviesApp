package com.emikhalets.moviesapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.emikhalets.moviesapp.R
import com.emikhalets.moviesapp.databinding.ItemCastBinding
import com.emikhalets.moviesapp.model.pojo.Crew
import com.emikhalets.moviesapp.utils.buildProfile185px

class CrewAdapter(
        private val imageCornerRadius: Float,
        private val clickListener: (Int) -> Unit
) : ListAdapter<Crew, CrewAdapter.ViewHolder>(CrewDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.newInstance(parent, imageCornerRadius)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener { clickListener.invoke(getItem(position).id) }
    }

    class ViewHolder(
            private val binding: ItemCastBinding,
            private val imageCornerRadius: Float
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Crew) {
            with(binding) {
                imagePhoto.load(item.profile_path?.let { buildProfile185px(it) }) {
                    placeholder(R.drawable.ph_actor)
                    fallback(R.drawable.ph_actor)
                    transformations(RoundedCornersTransformation(imageCornerRadius))
                }
                textName.text = item.name
                textBottom.text = itemView.context.getString(
                        R.string.item_cast_details_text_bottom,
                        item.known_for_department,
                        item.job
                )
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

    class CrewDiffCallback : DiffUtil.ItemCallback<Crew>() {

        override fun areItemsTheSame(oldItem: Crew, newItem: Crew): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Crew, newItem: Crew): Boolean {
            return oldItem == newItem
        }
    }
}