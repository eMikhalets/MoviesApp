package com.emikhalets.moviesapp.view

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.emikhalets.moviesapp.databinding.FragmentHomeBinding
import com.emikhalets.moviesapp.utils.HomeNavigation
import com.emikhalets.moviesapp.utils.MovieQueries
import com.emikhalets.moviesapp.view.adapters.HomePersonAdapter
import com.emikhalets.moviesapp.view.adapters.MoviesListAdapter
import com.emikhalets.moviesapp.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val navClickListener: HomeNavigation?
        get() = requireActivity() as? HomeNavigation?

    private var personsAdapter: HomePersonAdapter? = null
    private var moviesPopularAdapter: MoviesListAdapter? = null
    private var moviesNowPlayingAdapter: MoviesListAdapter? = null
    private var moviesTopRatedAdapter: MoviesListAdapter? = null
    private var moviesUpcomingAdapter: MoviesListAdapter? = null

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerAdapters()
        if (savedInstanceState == null) homeViewModel.loadData()

        with(homeViewModel) {
            popArtists.observe(viewLifecycleOwner, { list ->
                personsAdapter?.submitList(list)
            })
            moviesPopular.observe(viewLifecycleOwner, { list ->
                moviesPopularAdapter?.submitList(list)
            })
            moviesNowPlaying.observe(viewLifecycleOwner, { list ->
                moviesNowPlayingAdapter?.submitList(list)
            })
            moviesTopRated.observe(viewLifecycleOwner, { list ->
                moviesTopRatedAdapter?.submitList(list)
            })
            moviesUpcoming.observe(viewLifecycleOwner, { list ->
                moviesUpcomingAdapter?.submitList(list)
            })
            uiVisibility.observe(viewLifecycleOwner, { isUiReady ->
                setInterfaceVisibility(isUiReady)
            })
            notice.observe(viewLifecycleOwner, { msg ->
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
            })
        }

        with(binding) {
            textPopArtistsAll.setOnClickListener { onAllPopArtistClick() }
            textPopMoviesAll.setOnClickListener { onAllMovieClick(MovieQueries.POPULAR) }
            textPlayingMoviesAll.setOnClickListener { onAllMovieClick(MovieQueries.NOW_PLAYING) }
            textTopMoviesAll.setOnClickListener { onAllMovieClick(MovieQueries.TOP_RATED) }
            textUpcomingMoviesAll.setOnClickListener { onAllMovieClick(MovieQueries.UPCOMING) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.listPopArtists.adapter = null
        binding.listPopMovies.adapter = null
        binding.listPlayingMovies.adapter = null
        binding.listTopMovies.adapter = null
        binding.listUpcomingMovies.adapter = null
        _binding = null
    }

    private fun initRecyclerAdapters() {
        val imageCornerRadius = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            2f,
            resources.displayMetrics
        )
        personsAdapter = HomePersonAdapter(imageCornerRadius) { onPopArtistClick(it) }
        moviesPopularAdapter = MoviesListAdapter(imageCornerRadius) { onMovieClick(it) }
        moviesNowPlayingAdapter = MoviesListAdapter(imageCornerRadius) { onMovieClick(it) }
        moviesTopRatedAdapter = MoviesListAdapter(imageCornerRadius) { onMovieClick(it) }
        moviesUpcomingAdapter = MoviesListAdapter(imageCornerRadius) { onMovieClick(it) }

        with(binding) {
            listPopArtists.apply {
                setHasFixedSize(true)
                adapter = personsAdapter
            }
            listPopMovies.apply {
                setHasFixedSize(true)
                adapter = moviesPopularAdapter
            }
            listPlayingMovies.apply {
                setHasFixedSize(true)
                adapter = moviesNowPlayingAdapter
            }
            listTopMovies.apply {
                setHasFixedSize(true)
                adapter = moviesTopRatedAdapter
            }
            listUpcomingMovies.apply {
                setHasFixedSize(true)
                adapter = moviesUpcomingAdapter
            }
        }
    }

    private fun onAllPopArtistClick() {
        navClickListener?.navigateFromHomeToAllPersons()
    }

    private fun onPopArtistClick(artistId: Int) {
        navClickListener?.navigateFromHomeToPersonDetails(artistId)
    }

    private fun onAllMovieClick(query: MovieQueries) {
        navClickListener?.navigateFromHomeToAllMovies(query)
    }

    private fun onMovieClick(movieId: Int) {
        navClickListener?.navigateFromHomeToMovieDetails(movieId)
    }

    private fun setInterfaceVisibility(bool: Boolean) {
        val duration = 500L
        with(binding) {
            pbLoadingData.animate().alpha(alpha(!bool)).setDuration(duration).start()
            textPopArtistsLabel.animate().alpha(alpha(bool)).setDuration(duration).start()
            textPopArtistsAll.animate().alpha(alpha(bool)).setDuration(duration).start()
            listPopMovies.animate().alpha(alpha(bool)).setDuration(duration).start()
            textPopMoviesLabel.animate().alpha(alpha(bool)).setDuration(duration).start()
            textPopMoviesAll.animate().alpha(alpha(bool)).setDuration(duration).start()
            listPopArtists.animate().alpha(alpha(bool)).setDuration(duration).start()
            textPlayingMoviesLabel.animate().alpha(alpha(bool)).setDuration(duration).start()
            textPlayingMoviesAll.animate().alpha(alpha(bool)).setDuration(duration).start()
            listPlayingMovies.animate().alpha(alpha(bool)).setDuration(duration).start()
            textTopMoviesLabel.animate().alpha(alpha(bool)).setDuration(duration).start()
            textTopMoviesAll.animate().alpha(alpha(bool)).setDuration(duration).start()
            listTopMovies.animate().alpha(alpha(bool)).setDuration(duration).start()
            textUpcomingMoviesLabel.animate().alpha(alpha(bool)).setDuration(duration).start()
            textUpcomingMoviesAll.animate().alpha(alpha(bool)).setDuration(duration).start()
            listUpcomingMovies.animate().alpha(alpha(bool)).setDuration(duration).start()
        }
    }

    private fun alpha(isVisible: Boolean): Float {
        return if (isVisible) 1f else 0f
    }

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}