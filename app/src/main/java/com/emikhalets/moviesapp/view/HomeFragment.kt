package com.emikhalets.moviesapp.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.emikhalets.moviesapp.R
import com.emikhalets.moviesapp.databinding.FragmentHomeBinding
import com.emikhalets.moviesapp.utils.*
import com.emikhalets.moviesapp.view.adapters.HomePersonAdapter
import com.emikhalets.moviesapp.view.adapters.MoviesListAdapter
import com.emikhalets.moviesapp.view.adapters.MoviesPagerAdapter
import com.emikhalets.moviesapp.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val navClickListener: HomeNavigation?
        get() = requireActivity() as? HomeNavigation?

    private var searchAdapter: MoviesPagerAdapter? = null
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
        if (savedInstanceState == null) {
            homeViewModel.isNightMode = requireActivity()
                .getSharedPreferences(SP_NAME, Activity.MODE_PRIVATE)
                .getBoolean(SP_THEME, false)
            homeViewModel.loadData()
        }

        with(homeViewModel) {
            btn_dark_theme.setOnClickListener { onThemeChangeClick() }
            search?.observe(viewLifecycleOwner, { searchAdapter?.submitList(it) })
            popArtists.observe(viewLifecycleOwner, { personsAdapter?.submitList(it) })
            moviesPopular.observe(viewLifecycleOwner, { moviesPopularAdapter?.submitList(it) })
            moviesPlaying.observe(viewLifecycleOwner, { moviesNowPlayingAdapter?.submitList(it) })
            moviesTopRated.observe(viewLifecycleOwner, { moviesTopRatedAdapter?.submitList(it) })
            moviesUpcoming.observe(viewLifecycleOwner, { moviesUpcomingAdapter?.submitList(it) })
            visibility.observe(viewLifecycleOwner, { setInterfaceVisibility(it) })
            notice.observe(viewLifecycleOwner, {
                binding.textError.text = it
                updateVisibility(HomeState.ERROR)
            })
        }

        with(binding) {
            val closeSearchId = searchView.context.resources
                .getIdentifier("android:id/search_close_btn", null, null)
            val closeSearch = searchView.findViewById<ImageView>(closeSearchId)
            searchView.setOnQueryTextListener(onSearchClick())
            closeSearch.setOnClickListener { onCloseListener() }
            textPopArtistsAll.setOnClickListener { onAllPopArtistClick() }
            textPopMoviesAll.setOnClickListener { onAllMovieClick(MovieQueries.POPULAR) }
            textPlayingMoviesAll.setOnClickListener { onAllMovieClick(MovieQueries.NOW_PLAYING) }
            textTopMoviesAll.setOnClickListener { onAllMovieClick(MovieQueries.TOP_RATED) }
            textUpcomingMoviesAll.setOnClickListener { onAllMovieClick(MovieQueries.UPCOMING) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.listSearch.adapter = null
        binding.listPopArtists.adapter = null
        binding.listPopMovies.adapter = null
        binding.listPlayingMovies.adapter = null
        binding.listTopMovies.adapter = null
        binding.listUpcomingMovies.adapter = null
        _binding = null
    }

    private fun initRecyclerAdapters() {
        val imageCornerRadius = imageCornerValue(resources)
        searchAdapter = MoviesPagerAdapter(imageCornerRadius) { onSearchMovieClick(it) }
        personsAdapter = HomePersonAdapter(imageCornerRadius) { onPopArtistClick(it) }
        moviesPopularAdapter = MoviesListAdapter(imageCornerRadius) { onMovieClick(it) }
        moviesNowPlayingAdapter = MoviesListAdapter(imageCornerRadius) { onMovieClick(it) }
        moviesTopRatedAdapter = MoviesListAdapter(imageCornerRadius) { onMovieClick(it) }
        moviesUpcomingAdapter = MoviesListAdapter(imageCornerRadius) { onMovieClick(it) }

        with(binding) {
            listSearch.apply {
                setHasFixedSize(true)
                adapter = searchAdapter
            }
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

    private fun onThemeChangeClick() {
        requireActivity().window.setWindowAnimations(R.style.ThemeChangingAnim)
        val isNightMode = homeViewModel.isNightMode
        if (isNightMode) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        requireActivity().getSharedPreferences(SP_NAME, Activity.MODE_PRIVATE).edit()
            .putBoolean(SP_THEME, isNightMode).apply()
    }

    private fun onSearchClick(): SearchView.OnQueryTextListener {
        return object : CustomSearchTextListener() {
            override fun onQueryTextSubmit(query: String): Boolean {
                return if (query.isNotEmpty()) {
                    homeViewModel.updateVisibility(HomeState.LOADING)
                    homeViewModel.initSearchPager(query)
                    binding.searchView.clearFocus()
                    homeViewModel.search?.observe(viewLifecycleOwner, { list ->
                        searchAdapter?.submitList(list)
                        homeViewModel.updateVisibility(HomeState.SEARCH)
                    })
                    true
                } else {
                    false
                }
            }
        }
    }

    private fun onCloseListener() {
        homeViewModel.search = null
        homeViewModel.updateVisibility(HomeState.DEFAULT)
        binding.searchView.onActionViewCollapsed()
    }

    private fun onSearchMovieClick(movieId: Int) {
        navClickListener?.navigateFromHomeToSearchMovie(movieId)
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

    private fun setInterfaceVisibility(state: HomeState) {
        with(binding) {

            when (state) {
                HomeState.DEFAULT -> {
                    scrollView.visibility = View.VISIBLE
                    textError.visibility = View.GONE
                    listSearch.visibility = View.GONE
                    pbLoadingData.visibility = View.GONE
                }
                HomeState.SEARCH -> {
                    listSearch.visibility = View.VISIBLE
                    textError.visibility = View.GONE
                    scrollView.visibility = View.GONE
                    pbLoadingData.visibility = View.GONE
                }
                HomeState.LOADING -> {
                    pbLoadingData.visibility = View.VISIBLE
                    textError.visibility = View.GONE
                    listSearch.visibility = View.GONE
                    scrollView.visibility = View.GONE
                }
                HomeState.ERROR -> {
                    textError.visibility = View.VISIBLE
                    pbLoadingData.visibility = View.GONE
                    listSearch.visibility = View.GONE
                    scrollView.visibility = View.GONE
                }
            }
        }
    }

    companion object {
        private const val SP_NAME = "shared_preferences_name"
        private const val SP_THEME = "shared_theme"

        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}