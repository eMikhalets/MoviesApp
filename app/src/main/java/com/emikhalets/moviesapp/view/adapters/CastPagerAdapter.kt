package com.emikhalets.moviesapp.view.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.emikhalets.moviesapp.view.CastListFragment

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