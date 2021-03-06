package com.emikhalets.moviesapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.emikhalets.moviesapp.R
import com.emikhalets.moviesapp.databinding.FragmentMovieDetailsBinding
import com.emikhalets.moviesapp.model.pojo.ResponseMovieId
import com.emikhalets.moviesapp.model.pojo.ResultReview
import com.emikhalets.moviesapp.utils.*
import com.emikhalets.moviesapp.view.adapters.CastAdapter
import com.emikhalets.moviesapp.view.adapters.MoviesListAdapter
import com.emikhalets.moviesapp.view.adapters.ReviewsAdapter
import com.emikhalets.moviesapp.viewmodel.MovieDetailsViewModel

class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    private val navClickListener: MovieDetailsNavigation?
        get() = requireActivity() as? MovieDetailsNavigation?

    private var castAdapter: CastAdapter? = null
    private var reviewsAdapter: ReviewsAdapter? = null
    private var moviesSimilarAdapter: MoviesListAdapter? = null

    private val movieDetailsViewModel: MovieDetailsViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerAdapters()
        if (savedInstanceState == null) {
            arguments?.let {
                val movieId = it.getInt(MOVIE_ID)
                movieDetailsViewModel.loadMovieData(movieId)
            }
        } else {
            binding.scrollMovie.scrollTo(movieDetailsViewModel.scrollPos, movieDetailsViewModel.scrollPos)
        }

        with(movieDetailsViewModel) {
            movie.observe(viewLifecycleOwner, { movieData ->
                if (savedInstanceState == null) movieDetailsViewModel.loadOtherData()
                setData(movieData)
            })
            cast.observe(viewLifecycleOwner, { castAdapter?.submitList(it) })
            reviews.observe(viewLifecycleOwner, { reviewsAdapter?.submitList(it) })
            moviesSimilar.observe(viewLifecycleOwner, { moviesSimilarAdapter?.submitList(it) })
            uiVisibility.observe(viewLifecycleOwner, { setInterfaceVisibility(it) })
            notice.observe(viewLifecycleOwner, { msg ->
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
            })
        }

        with(binding) {
            textShowAllCast.setOnClickListener { navigateToCastList() }
            textShowAllReviews.setOnClickListener { navigateToReviewsList() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        movieDetailsViewModel.scrollPos = binding.scrollMovie.scrollY
        binding.listCast.adapter = null
        binding.listReviews.adapter = null
        binding.listSimilarMovies.adapter = null
        _binding = null
    }

    private fun initRecyclerAdapters() {
        val imageCornerRadius = imageCornerValue(resources)
        castAdapter = CastAdapter(imageCornerRadius) { onCastClick(it) }
        reviewsAdapter = ReviewsAdapter(imageCornerRadius) { onReviewClick(it) }
        moviesSimilarAdapter = MoviesListAdapter(imageCornerRadius) { onSimilarMovieClick(it) }

        with(binding) {
            listCast.apply {
                adapter = castAdapter
                isNestedScrollingEnabled = false
            }
            listReviews.apply {
                adapter = reviewsAdapter
                isNestedScrollingEnabled = false
            }
            listSimilarMovies.apply {
                setHasFixedSize(true)
                adapter = moviesSimilarAdapter
            }
        }
    }

    private fun onCastClick(personId: Int) {
        navClickListener?.navigateFromMovieDetailsToPersonDetails(personId)
    }

    private fun navigateToCastList() {
        navClickListener?.navigateFromMovieDetailsToPersonList(movieDetailsViewModel.id)
    }

    private fun onReviewClick(review: ResultReview) {
        if (!this.isHidden) navClickListener?.navigateFromMovieDetailsToReviewDetails(review)
    }

    private fun navigateToReviewsList() {
        navClickListener?.navigateFromMovieDetailsToReviewsList(movieDetailsViewModel.id)
    }

    private fun onSimilarMovieClick(movieId: Int) {
        navClickListener?.navigateFromMovieDetailsToSimilarMovieDetails(movieId)
    }

    private fun onImageClick(path: String) {
        val list = formatImagesList(path)
        navClickListener?.navigateFromMovieDetailsToImageZoom(list, 0)
    }

    private fun setData(data: ResponseMovieId) {
        with(binding) {
            data.backdrop_path?.let { img ->
                imageBackdrop.load(buildBackdrop780px(img)) { fallback(R.drawable.ph_backdrop) }
                imageBackdrop.setOnClickListener { onImageClick(img) }
            }
            data.poster_path?.let { img ->
                imagePoster.load(buildPoster185px(img)) { fallback(R.drawable.ph_poster) }
                imagePoster.setOnClickListener { onImageClick(img) }
            }
            textName.text = data.title
            textAgeRating.text = setCertification(data.certification)
            textYear.text = movieDetailsViewModel.parseYear(data.release_date)
            ratingBar.rating = (data.vote_average / 2).toFloat()
            textRating.text = getString(
                    R.string.variable_rating,
                    data.vote_average.toString()
            )
            textTags.text = data.genres
            textRuntime.text = getString(
                    R.string.variable_runtime,
                    data.runtime
            )
            textStory.text = data.overview
            textStatusContent.text = data.status
            textBudgetContent.text = getString(
                    R.string.variable_money,
                    data.budget
            )
            textRevenueContent.text = getString(
                    R.string.variable_money,
                    data.revenue
            )
        }
    }

    private fun setCertification(cert: String): String {
        return if (cert.isNotEmpty()) {
            binding.textAgeRating.visibility = View.VISIBLE
            cert
        } else {
            binding.textAgeRating.visibility = View.GONE
            ""
        }
    }

    private fun setInterfaceVisibility(bool: Boolean) {
        val duration = 500L
        with(binding) {
            pbLoadingData.animate().alpha(alpha(!bool)).setDuration(duration).start()
            imageBackdrop.animate().alpha(alpha(bool)).setDuration(duration).start()
            imagePoster.animate().alpha(alpha(bool)).setDuration(duration).start()
            textName.animate().alpha(alpha(bool)).setDuration(duration).start()
            textAgeRating.animate().alpha(alpha(bool)).setDuration(duration).start()
            textYear.animate().alpha(alpha(bool)).setDuration(duration).start()
            ratingBar.animate().alpha(alpha(bool)).setDuration(duration).start()
            textRating.animate().alpha(alpha(bool)).setDuration(duration).start()
            textTags.animate().alpha(alpha(bool)).setDuration(duration).start()
            textRuntime.animate().alpha(alpha(bool)).setDuration(duration).start()
            textStoryLabel.animate().alpha(alpha(bool)).setDuration(duration).start()
            textStory.animate().alpha(alpha(bool)).setDuration(duration).start()
            textDetailsLabel.animate().alpha(alpha(bool)).setDuration(duration).start()
            textStatus.animate().alpha(alpha(bool)).setDuration(duration).start()
            textStatusContent.animate().alpha(alpha(bool)).setDuration(duration).start()
            textBudget.animate().alpha(alpha(bool)).setDuration(duration).start()
            textBudgetContent.animate().alpha(alpha(bool)).setDuration(duration).start()
            textRevenue.animate().alpha(alpha(bool)).setDuration(duration).start()
            textRevenueContent.animate().alpha(alpha(bool)).setDuration(duration).start()
            textCastLabel.animate().alpha(alpha(bool)).setDuration(duration).start()
            listCast.animate().alpha(alpha(bool)).setDuration(duration).start()
            textShowAllCast.animate().alpha(alpha(bool)).setDuration(duration).start()
            textReviewsLabel.animate().alpha(alpha(bool)).setDuration(duration).start()
            listReviews.animate().alpha(alpha(bool)).setDuration(duration).start()
            textShowAllReviews.animate().alpha(alpha(bool)).setDuration(duration).start()
            textSimilarLabel.animate().alpha(alpha(bool)).setDuration(duration).start()
            listSimilarMovies.animate().alpha(alpha(bool)).setDuration(duration).start()
        }
    }

    private fun alpha(isVisible: Boolean): Float {
        return if (isVisible) 1f else 0f
    }

    companion object {
        private const val MOVIE_ID = "movie_id"

        fun newInstance(movieId: Int): MovieDetailsFragment {
            val bundle = bundleOf(MOVIE_ID to movieId)
            val fragment = MovieDetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}