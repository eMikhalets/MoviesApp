package com.emikhalets.moviesapp.view

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.emikhalets.moviesapp.databinding.FragmentCastListBinding
import com.emikhalets.moviesapp.utils.MovieQueries
import com.emikhalets.moviesapp.view.adapters.CastAdapter
import com.emikhalets.moviesapp.viewmodel.CastPagerViewModel

class MovieListFragment : Fragment() {

    private var _binding: FragmentCastListBinding? = null
    private val binding get() = _binding!!

    private var castAdapter: CastAdapter? = null
    private val castPagerViewModel: CastPagerViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCastListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerAdapter()

        castPagerViewModel.cast.observe(viewLifecycleOwner, { list ->
            castAdapter?.submitList(list)
        })
        castPagerViewModel.notice.observe(viewLifecycleOwner, { msg ->
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.listCast.adapter = null
        _binding = null
    }

    private fun initRecyclerAdapter() {
        val imageCornerRadius = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                2f,
                resources.displayMetrics
        )
        castAdapter = CastAdapter(imageCornerRadius) { onMovieClick(it) }

        with(binding) {
            listCast.apply {
                setHasFixedSize(true)
                adapter = castAdapter
            }
        }
    }

    private fun onMovieClick(movieId: Int) {
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