package com.emikhalets.moviesapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

    // From Home page navigation

    override fun navigateFromHomeToSearchMovie(movieId: Int) {
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, MovieDetailsFragment.newInstance(movieId))
                .addToBackStack(BACK_STACK)
                .commit()
    }

    override fun navigateFromHomeToPersonDetails(personId: Int) {
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, PersonDetailsFragment.newInstance(personId))
                .addToBackStack(BACK_STACK)
                .commit()
    }

    override fun navigateFromHomeToAllPersons() {
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, PersonListFragment.newInstance())
                .addToBackStack(BACK_STACK)
                .commit()
    }

    override fun navigateFromHomeToMovieDetails(movieId: Int) {
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, MovieDetailsFragment.newInstance(movieId))
                .addToBackStack(BACK_STACK)
                .commit()
    }

    override fun navigateFromHomeToAllMovies(query: MovieQueries) {
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, MovieListFragment.newInstance(query))
                .addToBackStack(BACK_STACK)
                .commit()
    }

    // From Person List page navigation

    override fun navigateFromPersonListToPersonDetails(personId: Int) {
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, PersonDetailsFragment.newInstance(personId))
                .addToBackStack(BACK_STACK)
                .commit()
    }

    // From Movie List page navigation

    override fun navigateFromMoviesListToMoviesDetails(movieId: Int) {
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, MovieDetailsFragment.newInstance(movieId))
                .addToBackStack(BACK_STACK)
                .commit()
    }

    // From Movie Details page navigation

    override fun navigateFromMovieDetailsToPersonDetails(personId: Int) {
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, PersonDetailsFragment.newInstance(personId))
                .addToBackStack(BACK_STACK)
                .commit()
    }

    override fun navigateFromMovieDetailsToPersonList(movieId: Int) {
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, CastPagerFragment.newInstance(movieId))
                .addToBackStack(BACK_STACK)
                .commit()
    }

    override fun navigateFromMovieDetailsToReviewDetails(review: ResultReview) {
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, ReviewDialog.newInstance(review))
                .addToBackStack(BACK_STACK)
                .commit()
    }

    override fun navigateFromMovieDetailsToReviewsList(movieId: Int) {
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, ReviewListFragment.newInstance(movieId))
                .addToBackStack(BACK_STACK)
                .commit()
    }

    override fun navigateFromMovieDetailsToSimilarMovieDetails(movieId: Int) {
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, MovieDetailsFragment.newInstance(movieId))
                .addToBackStack(BACK_STACK)
                .commit()
    }

    override fun navigateFromMovieDetailsToImageZoom(imagePaths: ArrayList<String>, position: Int) {
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, ImageZoomPagerFragment.newInstance(imagePaths, position))
                .addToBackStack(BACK_STACK)
                .commit()
    }

    // From Review List page navigation

    override fun navigateFromReviewListToReviewDetails(review: ResultReview) {
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, ReviewDialog.newInstance(review))
                .addToBackStack(BACK_STACK)
                .commit()
    }

    // From Person Details page navigation

    override fun navigateFromPersonDetailsToImageZoom(imagePaths: ArrayList<String>, position: Int) {
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, ImageZoomPagerFragment.newInstance(imagePaths, position))
                .addToBackStack(BACK_STACK)
                .commit()
    }

    companion object {
        private const val BACK_STACK = "back_stack"
    }
}