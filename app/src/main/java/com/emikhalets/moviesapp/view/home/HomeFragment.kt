package com.emikhalets.moviesapp.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.emikhalets.moviesapp.databinding.FragmentHomeBinding
import com.emikhalets.moviesapp.view.MoviesListAdapter
import com.emikhalets.moviesapp.view.PopArtistsAdapter
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
            moviesUpcomingAdapter?.submitList(list) })

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
        artistsAdapter = PopArtistsAdapter()
        moviesPopularAdapter = MoviesListAdapter()
        moviesNowPlayingAdapter = MoviesListAdapter()
        moviesTopRatedAdapter = MoviesListAdapter()
        moviesUpcomingAdapter = MoviesListAdapter()

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
}