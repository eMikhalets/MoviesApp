package com.emikhalets.moviesapp.view.movie_details

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.load
import com.emikhalets.moviesapp.R
import com.emikhalets.moviesapp.databinding.FragmentMovieDetailsBinding
import com.emikhalets.moviesapp.model.pojo.ResponseMovieId
import com.emikhalets.moviesapp.model.pojo.ResultReview
import com.emikhalets.moviesapp.utils.MovieDetailsNavigation
import com.emikhalets.moviesapp.utils.buildBackdropUrl1280px
import com.emikhalets.moviesapp.utils.buildPosterUrl780px
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

    private val movieDetailsViewModel: MovieDetailsViewModel by activityViewModels()

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
                val movieId = it.getInt(MOVIE_ID) ?: -1
                movieDetailsViewModel.loadMovieData(movieId)
            }
        }

        movieDetailsViewModel.movie.observe(viewLifecycleOwner, { movieData ->
            if (savedInstanceState == null) movieDetailsViewModel.loadOtherData()
            setData(movieData)
        })
        movieDetailsViewModel.cast.observe(viewLifecycleOwner, { list ->
            castAdapter?.submitList(list)
        })
        movieDetailsViewModel.reviews.observe(viewLifecycleOwner, { reviews ->
            reviewsAdapter?.submitList(reviews)
        })
        movieDetailsViewModel.moviesSimilar.observe(viewLifecycleOwner, { list ->
            moviesSimilarAdapter?.submitList(list)
        })
        movieDetailsViewModel.uiVisibility.observe(viewLifecycleOwner, { isDataLoaded ->
            setInterfaceVisibility(isDataLoaded, movieDetailsViewModel.getMovie())
        })
        movieDetailsViewModel.notice.observe(viewLifecycleOwner, { msg ->
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
        })

        binding.textShowAllCast.setOnClickListener { navigateToCastList() }
        binding.textShowAllReviews.setOnClickListener { navigateToReviewsList() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.listCast.adapter = null
        binding.listReviews.adapter = null
        binding.listSimilarMovies.adapter = null
        _binding = null
    }

    private fun initRecyclerAdapters() {
        val imageCornerRadius = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            2f,
            resources.displayMetrics
        )
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

    private fun onCastClick(creditId: String) {
        navClickListener?.navigateFromMovieDetailsToCastDetails(creditId)
    }

    private fun navigateToCastList() {
        navClickListener?.navigateFromMovieDetailsToCastList(movieDetailsViewModel.id)
    }

    private fun onReviewClick(review: ResultReview) {
        navClickListener?.navigateFromMovieDetailsToReviewDetails(review)
    }

    private fun navigateToReviewsList() {
        navClickListener?.navigateFromMovieDetailsToReviewsList(movieDetailsViewModel.id)
    }

    private fun onSimilarMovieClick(movieId: Int) {
        navClickListener?.navigateFromMovieDetailsToSimilarMovieDetails(movieId)
    }

    private fun setData(data: ResponseMovieId) {
        with(binding) {
            imageBackdrop.load(data.backdrop_path?.let { buildBackdropUrl1280px(it) }) {
                fallback(R.drawable.ph_backdrop)
            }
            imagePoster.load(data.poster_path?.let { buildPosterUrl780px(it) }) {
                fallback(R.drawable.ph_poster)
            }
            textName.text = data.title
//            textAgeRating.text =
            textYear.text = movieDetailsViewModel.parseYear(data.release_date)
            ratingBar.rating = (data.vote_average / 2).toFloat()
            textRating.text = getString(
                R.string.text_rating,
                data.vote_average.toInt()
            )
            textTags.text = movieDetailsViewModel.parseGenres(data.genres)
            textRuntime.text = getString(
                R.string.text_runtime,
                data.runtime
            )
            textStory.text = data.overview
            textStatusContent.text = data.status
            textBudgetContent.text = getString(
                R.string.text_money,
                data.budget
            )
            textRevenueContent.text = getString(
                R.string.text_money,
                data.revenue
            )
        }
    }

    private fun setInterfaceVisibility(bool: Boolean, data: ResponseMovieId?) {
        val duration = 500L
        with(binding) {
            pbLoadingData.animate().alpha(alpha(!bool)).setDuration(duration).start()
            textName.animate().alpha(alpha(bool)).setDuration(duration).start()
            textAgeRating.animate().alpha(alpha(bool)).setDuration(duration).start()
            textYear.animate().alpha(alpha(bool)).setDuration(duration).start()
            ratingBar.animate().alpha(alpha(bool)).setDuration(duration).start()
            textTags.animate().alpha(alpha(bool)).setDuration(duration).start()
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
            data?.let {
                it.backdrop_path?.let {
                    imageBackdrop.animate().alpha(alpha(bool)).setDuration(duration).start()
                }
                it.poster_path?.let {
                    imagePoster.animate().alpha(alpha(bool)).setDuration(duration).start()
                }
                it.overview?.let {
                    textStory.animate().alpha(alpha(bool)).setDuration(duration).start()
                    textStoryLabel.animate().alpha(alpha(bool)).setDuration(duration).start()
                }
                it.runtime?.let {
                    textRuntime.animate().alpha(alpha(bool)).setDuration(duration).start()
                }
                textRuntime.animate().alpha(alpha(bool)).setDuration(duration).start()
            }
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