package com.emikhalets.moviesapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import coil.load
import com.emikhalets.moviesapp.R
import com.emikhalets.moviesapp.databinding.DialogReviewBinding
import com.emikhalets.moviesapp.model.pojo.ResultReview
import com.emikhalets.moviesapp.utils.buildProfile185px
import java.text.SimpleDateFormat
import java.util.*

class ReviewDialog : DialogFragment() {

    private var _binding: DialogReviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = DialogReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val review = it.getParcelable(REVIEW) as? ResultReview?
            setData(review)
        }
    }

    private fun setData(review: ResultReview?) {
        review?.let {
            with(binding) {
                imageAvatar.load(it.author_details.avatar_path?.let { path -> buildProfile185px(path) })
                textName.text = it.author
                textRating.text = getString(
                        R.string.text_rating,
                        it.author_details.rating
                )
                textUpdated.text = formatDate(it.updated_at)
                textContent.text = it.content
            }
        }
    }

    private fun formatDate(dateStr: String): String {
        val formatIn = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.getDefault())
        val formatOut = SimpleDateFormat("dd-MMMM-yyyy", Locale.getDefault())
        val date = formatIn.parse(dateStr) ?: Date()
        return formatOut.format(date)
    }

    companion object {
        private const val REVIEW = "review"

        fun newInstance(review: ResultReview): ReviewDialog {
            val bundle = bundleOf(REVIEW to review)
            val fragment = ReviewDialog()
            fragment.arguments = bundle
            return fragment
        }
    }
}