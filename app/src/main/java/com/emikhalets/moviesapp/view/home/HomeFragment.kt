package com.emikhalets.moviesapp.view.home

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.emikhalets.moviesapp.databinding.FragmentHomeBinding
import com.emikhalets.moviesapp.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var artistsAdapter: PopArtistsAdapter? = null
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

        homeViewModel.popArtists.observe(viewLifecycleOwner, { list ->
            artistsAdapter?.submitList(list)
        })
        homeViewModel.moviesPopular.observe(viewLifecycleOwner, { list ->
            moviesPopularAdapter?.submitList(list)
        })
        homeViewModel.moviesNowPlaying.observe(viewLifecycleOwner, { list ->
            moviesNowPlayingAdapter?.submitList(list)
        })
        homeViewModel.moviesTopRated.observe(viewLifecycleOwner, { list ->
            moviesTopRatedAdapter?.submitList(list)
        })
        homeViewModel.moviesUpcoming.observe(viewLifecycleOwner, { list ->
            moviesUpcomingAdapter?.submitList(list)
        })
        homeViewModel.uiVisibility.observe(viewLifecycleOwner, { isUiReady ->
            setInterfaceVisibility(isUiReady)
        })
        homeViewModel.notice.observe(viewLifecycleOwner, { msg ->
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
        })
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
        artistsAdapter = PopArtistsAdapter(imageCornerRadius)
        moviesPopularAdapter = MoviesListAdapter(imageCornerRadius)
        moviesNowPlayingAdapter = MoviesListAdapter(imageCornerRadius)
        moviesTopRatedAdapter = MoviesListAdapter(imageCornerRadius)
        moviesUpcomingAdapter = MoviesListAdapter(imageCornerRadius)

        with(binding) {
            listPopArtists.apply {
                setHasFixedSize(true)
                adapter = artistsAdapter
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

    private fun setInterfaceVisibility(isVisible: Boolean) {
        val duration = 500L
        val visible = 1f
        val invisible = 0f
        with(binding) {
            if (isVisible) {
                pbLoadingData.animate().alpha(invisible).setDuration(duration).start()
                textPopArtistsLabel.animate().alpha(visible).setDuration(duration).start()
                textPopArtistsAll.animate().alpha(visible).setDuration(duration).start()
                listPopMovies.animate().alpha(visible).setDuration(duration).start()
                textPopMoviesLabel.animate().alpha(visible).setDuration(duration).start()
                textPopMoviesAll.animate().alpha(visible).setDuration(duration).start()
                listPopArtists.animate().alpha(visible).setDuration(duration).start()
                textPlayingMoviesLabel.animate().alpha(visible).setDuration(duration).start()
                textPlayingMoviesAll.animate().alpha(visible).setDuration(duration).start()
                listPlayingMovies.animate().alpha(visible).setDuration(duration).start()
                textTopMoviesLabel.animate().alpha(visible).setDuration(duration).start()
                textTopMoviesAll.animate().alpha(visible).setDuration(duration).start()
                listTopMovies.animate().alpha(visible).setDuration(duration).start()
                textUpcomingMoviesLabel.animate().alpha(visible).setDuration(duration).start()
                textUpcomingMoviesAll.animate().alpha(visible).setDuration(duration).start()
                listUpcomingMovies.animate().alpha(visible).setDuration(duration).start()
            } else {
                pbLoadingData.animate().alpha(visible).setDuration(duration).start()
                textPopArtistsLabel.animate().alpha(invisible).setDuration(duration).start()
                textPopArtistsAll.animate().alpha(invisible).setDuration(duration).start()
                listPopArtists.animate().alpha(invisible).setDuration(duration).start()
                textPopMoviesLabel.animate().alpha(invisible).setDuration(duration).start()
                textPopMoviesAll.animate().alpha(invisible).setDuration(duration).start()
                listPopArtists.animate().alpha(invisible).setDuration(duration).start()
                textPlayingMoviesLabel.animate().alpha(invisible).setDuration(duration).start()
                textPlayingMoviesAll.animate().alpha(invisible).setDuration(duration).start()
                listPlayingMovies.animate().alpha(invisible).setDuration(duration).start()
                textTopMoviesLabel.animate().alpha(invisible).setDuration(duration).start()
                textTopMoviesAll.animate().alpha(invisible).setDuration(duration).start()
                listTopMovies.animate().alpha(invisible).setDuration(duration).start()
                textUpcomingMoviesLabel.animate().alpha(invisible).setDuration(duration).start()
                textUpcomingMoviesAll.animate().alpha(invisible).setDuration(duration).start()
                listUpcomingMovies.animate().alpha(invisible).setDuration(duration).start()
            }
        }
    }
}