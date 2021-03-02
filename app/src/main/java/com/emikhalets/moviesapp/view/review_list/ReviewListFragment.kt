package com.emikhalets.moviesapp.view.review_list

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.emikhalets.moviesapp.databinding.FragmentReviewsListBinding
import com.emikhalets.moviesapp.model.pojo.ResultReview
import com.emikhalets.moviesapp.utils.ReviewListNavigation
import com.emikhalets.moviesapp.view.adapters.ReviewsAdapter
import com.emikhalets.moviesapp.viewmodel.ReviewListViewModel

class ReviewListFragment : Fragment() {

    private var _binding: FragmentReviewsListBinding? = null
    private val binding get() = _binding!!

    private val navClickListener: ReviewListNavigation?
        get() = requireActivity() as? ReviewListNavigation?

    private var reviewsAdapter: ReviewsAdapter? = null
    private val reviewsViewModel: ReviewListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReviewsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerAdapters()
        if (savedInstanceState == null) {
            arguments?.let {
                val movieId = it.getInt(MOVIE_ID) ?: -1
                reviewsViewModel.loadData(movieId)
            }
        }

        reviewsViewModel.reviews.observe(viewLifecycleOwner, { list ->
            reviewsAdapter?.submitList(list)
        })
        reviewsViewModel.uiVisibility.observe(viewLifecycleOwner, { isUiReady ->
            setInterfaceVisibility(isUiReady)
        })
        reviewsViewModel.notice.observe(viewLifecycleOwner, { msg ->
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.listReviews.adapter = null
        _binding = null
    }

    private fun initRecyclerAdapters() {
        val imageCornerRadius = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            2f,
            resources.displayMetrics
        )
        reviewsAdapter = ReviewsAdapter(imageCornerRadius) { onReviewClick(it) }

        with(binding) {
            listReviews.apply {
                setHasFixedSize(true)
                adapter = reviewsAdapter
            }
        }
    }

    private fun onReviewClick(review: ResultReview) {
        navClickListener?.navigateFromReviewListToReview(review)
    }

    private fun setInterfaceVisibility(bool: Boolean) {
        val duration = 500L
        with(binding) {
            pbLoadingData.animate().alpha(alpha(!bool)).setDuration(duration).start()
            listReviews.animate().alpha(alpha(bool)).setDuration(duration).start()
        }
    }

    private fun alpha(isVisible: Boolean): Float {
        return if (isVisible) 1f else 0f
    }

    companion object {
        private const val MOVIE_ID = "movie_id"

        fun newInstance(movieId: Int): ReviewListFragment {
            val bundle = bundleOf(MOVIE_ID to movieId)
            val fragment = ReviewListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}