package com.emikhalets.moviesapp.view

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.emikhalets.moviesapp.databinding.FragmentMoviesListBinding
import com.emikhalets.moviesapp.utils.MovieQueries
import com.emikhalets.moviesapp.utils.MoviesListNavigation
import com.emikhalets.moviesapp.view.adapters.MoviesPagerAdapter
import com.emikhalets.moviesapp.viewmodel.MoviesPagerViewModel

class MovieListFragment : Fragment() {

    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!

    private val navClickListener: MoviesListNavigation?
        get() = requireActivity() as? MoviesListNavigation?

    private var moviesAdapter: MoviesPagerAdapter? = null
    private val moviesPagerViewModel: MoviesPagerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerAdapter()
        if (savedInstanceState == null) {
            arguments?.let {
                val query = it.getSerializable(MOVIE_QUERY) as MovieQueries
                moviesPagerViewModel.initPager(query)
                setInterfaceVisibility(false)
            }
        }
        moviesPagerViewModel.movies?.observe(viewLifecycleOwner, { list ->
            moviesAdapter?.submitList(list)
            setInterfaceVisibility(true)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.listMovies.adapter = null
        _binding = null
    }

    private fun initRecyclerAdapter() {
        val imageCornerRadius = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            2f,
            resources.displayMetrics
        )
        moviesAdapter = MoviesPagerAdapter(imageCornerRadius) { onMovieClick(it) }
        binding.listMovies.apply {
            setHasFixedSize(true)
            adapter = moviesAdapter
        }
    }

    private fun onMovieClick(movieId: Int) {
        navClickListener?.navigateFromMoviesListToMoviesDetails(movieId)
    }

    private fun setInterfaceVisibility(bool: Boolean) {
        val duration = 500L
        with(binding) {
            pbLoadingData.animate().alpha(alpha(!bool)).setDuration(duration).start()
            listMovies.animate().alpha(alpha(bool)).setDuration(duration).start()
        }
    }

    private fun alpha(isVisible: Boolean): Float {
        return if (isVisible) 1f else 0f
    }

    companion object {
        private const val MOVIE_QUERY = "movie_query"

        fun newInstance(query: MovieQueries): MovieListFragment {
            val bundle = bundleOf(MOVIE_QUERY to query)
            val fragment = MovieListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}