package com.emikhalets.moviesapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.emikhalets.moviesapp.R
import com.emikhalets.moviesapp.databinding.FragmentCastPagerBinding
import com.emikhalets.moviesapp.viewmodel.CastPagerViewModel
import com.google.android.material.tabs.TabLayoutMediator

class CastPagerFragment : Fragment() {

    private var _binding: FragmentCastPagerBinding? = null
    private val binding get() = _binding!!

    private var castPagerAdapter: CastPagerAdapter? = null
    private val castPagerViewModel: CastPagerViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCastPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPagerAdapters()
        if (savedInstanceState == null) {
            arguments?.let {
                val movieId = it.getInt(MOVIE_ID)
                castPagerViewModel.loadCastAndCrew(movieId)
            }
        }
        castPagerViewModel.uiVisibility.observe(viewLifecycleOwner) { isDataLoaded ->
            setInterfaceVisibility(isDataLoaded)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.viewPager.adapter = null
        _binding = null
    }

    private fun initPagerAdapters() {
        castPagerAdapter = CastPagerAdapter(this)
        binding.viewPager.adapter = castPagerAdapter
        attachTabs()
    }

    private fun attachTabs() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.cast_full_tab_cast)
                else -> tab.text = getString(R.string.cast_full_tab_crew)
            }
        }.attach()
    }

    private fun setInterfaceVisibility(bool: Boolean) {
        val duration = 500L
        with(binding) {
            pbLoadingData.animate().alpha(alpha(!bool)).setDuration(duration).start()
            viewPager.animate().alpha(alpha(bool)).setDuration(duration).start()
        }
    }

    private fun alpha(isVisible: Boolean): Float {
        return if (isVisible) 1f else 0f
    }

    companion object {
        private const val MOVIE_ID = "movie_id"

        fun newInstance(movieId: Int): CastPagerFragment {
            val bundle = bundleOf(MOVIE_ID to movieId)
            val fragment = CastPagerFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    class CastPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> CastListFragment.newInstance(0)
                else -> CastListFragment.newInstance(1)
            }
        }
    }
}