package com.emikhalets.moviesapp.utils

import com.emikhalets.moviesapp.model.pojo.ResultReview

interface HomeNavigation {
    fun navigateFromHomeToMovieDetails(id: Int)
    fun navigateFromHomeToActorDetails(id: Int)
}

interface MovieDetailsNavigation {
    fun navigateFromMovieDetailsToCastDetails(castId: String)
    fun navigateFromMovieDetailsToCastList(id: Int)
    fun navigateFromMovieDetailsToReviewDetails(review: ResultReview)
    fun navigateFromMovieDetailsToReviewsList(id: Int)
    fun navigateFromMovieDetailsToSimilarMovieDetails(id: Int)
}

interface ReviewListNavigation {
    fun navigateFromReviewListToReview(review: ResultReview)
}