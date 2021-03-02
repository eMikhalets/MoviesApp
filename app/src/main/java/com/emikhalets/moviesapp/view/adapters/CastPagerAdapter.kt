package com.emikhalets.moviesapp.view.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.emikhalets.moviesapp.view.cast_list.CastListFragment
import com.emikhalets.moviesapp.view.cast_list.CrewListFragment

class CastPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CastListFragment.newInstance()
            else -> CrewListFragment.newInstance()
        }
    }
}