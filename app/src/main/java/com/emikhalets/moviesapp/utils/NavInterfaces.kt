package com.emikhalets.moviesapp.utils

import com.emikhalets.moviesapp.model.pojo.ResultReview

interface HomeNavigation {
    fun navigateFromHomeToSearchMovie(movieId: Int)
    fun navigateFromHomeToPersonDetails(personId: Int)
    fun navigateFromHomeToAllPersons()
    fun navigateFromHomeToMovieDetails(movieId: Int)
    fun navigateFromHomeToAllMovies(query: MovieQueries)
}

interface PersonListNavigation {
    fun navigateFromPersonListToPersonDetails(personId: Int)
}

interface MoviesListNavigation {
    fun navigateFromMoviesListToMoviesDetails(movieId: Int)
}

interface MovieDetailsNavigation {
    fun navigateFromMovieDetailsToPersonDetails(personId: Int)
    fun navigateFromMovieDetailsToPersonList(movieId: Int)
    fun navigateFromMovieDetailsToReviewDetails(review: ResultReview)
    fun navigateFromMovieDetailsToReviewsList(movieId: Int)
    fun navigateFromMovieDetailsToSimilarMovieDetails(movieId: Int)
}

interface ReviewListNavigation {
    fun navigateFromReviewListToReviewDetails(review: ResultReview)
}