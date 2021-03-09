package com.emikhalets.moviesapp.view

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewTreeObserver
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import coil.load
import com.emikhalets.moviesapp.R
import com.emikhalets.moviesapp.databinding.DialogReviewBinding
import com.emikhalets.moviesapp.model.pojo.ResultReview
import com.emikhalets.moviesapp.utils.buildProfile185px
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.min

class ReviewDialog : DialogFragment() {

    private var _binding: DialogReviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogReviewBinding.inflate(LayoutInflater.from(context))
        arguments?.let { setData(it.getParcelable(REVIEW) as? ResultReview?) }
        val dialog = AlertDialog.Builder(requireActivity()).setView(binding.root)
        return dialog.create()
    }

    override fun onResume() {
        super.onResume()
        val decorView = dialog?.window?.decorView
        decorView?.viewTreeObserver?.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                decorView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val scrollingUsableHeight = binding.scrollReview.height + binding.scrollReview.paddingTop + binding.scrollReview.paddingBottom
                val childView = binding.scrollReview.getChildAt(0)
                if (childView.height <= scrollingUsableHeight) return
                val displayRectangle = Rect()
                decorView.getWindowVisibleDisplayFrame(displayRectangle)
                val decorUsableHeight = displayRectangle.height()
                val heightToFit = binding.reviewParent.height + childView.height - scrollingUsableHeight
                binding.reviewParent.minHeight = min(decorUsableHeight, heightToFit)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setData(review: ResultReview?) {
        review?.let {
            with(binding) {
                imageAvatar.load(it.author_details.avatar_path?.let { path -> buildProfile185px(path) }) {
                    fallback(R.drawable.ph_avatar)
                }
                textName.text = it.author
                textRating.text = getString(
                        R.string.variable_rating,
                        formatRating(it.author_details.rating)
                )
                textUpdated.text = formatDate(it.updated_at)
                textContent.text = it.content
            }
        }
    }

    private fun formatRating(rating: Double?): String {
        return rating?.toInt()?.toString() ?: "-"
    }

    private fun formatDate(dateStr: String): String {
        val formatIn = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.getDefault())
        val formatOut = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val date = formatIn.parse(dateStr) ?: Date()
        return formatOut.format(date)
    }

    companion object {
        private const val REVIEW = "review"

        fun newInstance(review: ResultReview): ReviewDialog {
            val bundle = bundleOf(REVIEW to review)
            val dialog = ReviewDialog()
            dialog.arguments = bundle
            return dialog
        }
    }
}