package com.emikhalets.moviesapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.emikhalets.moviesapp.R
import com.emikhalets.moviesapp.databinding.ActivityMainBinding
import com.emikhalets.moviesapp.model.pojo.ResultReview
import com.emikhalets.moviesapp.utils.HomeNavigation
import com.emikhalets.moviesapp.utils.MovieDetailsNavigation
import com.emikhalets.moviesapp.utils.ReviewListNavigation
import com.emikhalets.moviesapp.view.cast_list.CastPagerFragment
import com.emikhalets.moviesapp.view.home.HomeFragment
import com.emikhalets.moviesapp.view.movie_details.MovieDetailsFragment
import com.emikhalets.moviesapp.view.review_dialog.ReviewDialog
import com.emikhalets.moviesapp.view.review_list.ReviewListFragment

class MainActivity : AppCompatActivity(), HomeNavigation, MovieDetailsNavigation,
    ReviewListNavigation {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        savedInstanceState ?: supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, HomeFragment.newInstance())
            .commit()
    }

    override fun navigateFromHomeToActorDetails(id: Int) {
    }

    override fun navigateFromHomeToMovieDetails(id: Int) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, MovieDetailsFragment.newInstance(id))
            .addToBackStack(BACK_STACK)
            .commit()
    }

    override fun navigateFromMovieDetailsToCastDetails(castId: String) {
    }

    override fun navigateFromMovieDetailsToCastList(id: Int) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, CastPagerFragment.newInstance(id))
            .addToBackStack(BACK_STACK)
            .commit()
    }

    override fun navigateFromMovieDetailsToReviewDetails(review: ResultReview) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, ReviewDialog.newInstance(review))
            .addToBackStack(BACK_STACK)
            .commit()
    }

    override fun navigateFromMovieDetailsToReviewsList(id: Int) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, ReviewListFragment.newInstance(id))
            .addToBackStack(BACK_STACK)
            .commit()
    }

    override fun navigateFromMovieDetailsToSimilarMovieDetails(id: Int) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, MovieDetailsFragment.newInstance(id))
            .addToBackStack(BACK_STACK)
            .commit()
    }

    override fun navigateFromReviewListToReview(review: ResultReview) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, ReviewDialog.newInstance(review))
            .addToBackStack(BACK_STACK)
            .commit()
    }

    companion object {
        private const val BACK_STACK = "back_stack"
    }
}