package com.emikhalets.moviesapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.emikhalets.moviesapp.R
import com.emikhalets.moviesapp.databinding.ActivityMainBinding
import com.emikhalets.moviesapp.model.pojo.ResultReview
import com.emikhalets.moviesapp.utils.*

class MainActivity : AppCompatActivity(), HomeNavigation, MovieDetailsNavigation,
        ReviewListNavigation, PersonListNavigation, MoviesListNavigation, PersonDetailsNavigation {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        savedInstanceState ?: supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, HomeFragment.newInstance())
                .commit()
    }

    private fun navigate(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fade_in, 0, 0, R.anim.fade_out)
                .add(R.id.fragment_container, fragment)
                .addToBackStack(BACK_STACK)
                .commit()
    }

    // ================== From Home page navigation

    override fun navigateFromHomeToSearchMovie(movieId: Int) {
        navigate(MovieDetailsFragment.newInstance(movieId))
    }

    override fun navigateFromHomeToPersonDetails(personId: Int) {
        navigate(PersonDetailsFragment.newInstance(personId))
    }

    override fun navigateFromHomeToAllPersons() {
        navigate(PersonListFragment.newInstance())
    }

    override fun navigateFromHomeToMovieDetails(movieId: Int) {
        navigate(MovieDetailsFragment.newInstance(movieId))
    }

    override fun navigateFromHomeToAllMovies(query: MovieQueries) {
        navigate(MovieListFragment.newInstance(query))
    }

    // ================== From Person List page navigation

    override fun navigateFromPersonListToPersonDetails(personId: Int) {
        navigate(PersonDetailsFragment.newInstance(personId))
    }

    // ================== From Movie List page navigation

    override fun navigateFromMoviesListToMoviesDetails(movieId: Int) {
        navigate(MovieDetailsFragment.newInstance(movieId))
    }

    // ================== From Movie Details page navigation

    override fun navigateFromMovieDetailsToPersonDetails(personId: Int) {
        navigate(PersonDetailsFragment.newInstance(personId))
    }

    override fun navigateFromMovieDetailsToPersonList(movieId: Int) {
        navigate(CastPagerFragment.newInstance(movieId))
    }

    override fun navigateFromMovieDetailsToReviewDetails(review: ResultReview) {
        navigate(ReviewDialog.newInstance(review))
    }

    override fun navigateFromMovieDetailsToReviewsList(movieId: Int) {
        navigate(ReviewListFragment.newInstance(movieId))
    }

    override fun navigateFromMovieDetailsToSimilarMovieDetails(movieId: Int) {
        navigate(MovieDetailsFragment.newInstance(movieId))
    }

    override fun navigateFromMovieDetailsToImageZoom(paths: ArrayList<String>, position: Int) {
        navigate(ImageZoomPagerFragment.newInstance(paths, position))
    }

    // ================== From Review List page navigation

    override fun navigateFromReviewListToReviewDetails(review: ResultReview) {
        navigate(ReviewDialog.newInstance(review))
    }

    // ================== From Person Details page navigation

    override fun navigateFromPersonDetailsToImageZoom(paths: ArrayList<String>, position: Int) {
        navigate(ImageZoomPagerFragment.newInstance(paths, position))
    }

    companion object {
        private const val BACK_STACK = "back_stack"
    }
}