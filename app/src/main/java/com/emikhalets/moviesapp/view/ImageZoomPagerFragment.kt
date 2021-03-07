package com.emikhalets.moviesapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.emikhalets.moviesapp.databinding.FragmentImageZoomPagerBinding
import com.emikhalets.moviesapp.viewmodel.ImageZoomPagerViewModel

class ImageZoomPagerFragment : Fragment() {

    private var _binding: FragmentImageZoomPagerBinding? = null
    private val binding get() = _binding!!

    private var imageZoomPagerAdapter: ImageZoomPagerAdapter? = null
    private val imagesViewModel: ImageZoomPagerViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageZoomPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            imagesViewModel.images = it.getStringArrayList(IMAGES_LIST) ?: listOf()
            imagesViewModel.position = it.getInt(POSITION)
        }

        if (imagesViewModel.images.isNotEmpty()) {
            imageZoomPagerAdapter = ImageZoomPagerAdapter(this)
            binding.viewPagerZoom.adapter = imageZoomPagerAdapter
            if (imagesViewModel.position > 0) {
                binding.viewPagerZoom.setCurrentItem(imagesViewModel.position, false)
            }
        } else {
            Toast.makeText(requireContext(), "no images", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.viewPagerZoom.adapter = null
        _binding = null
    }

    companion object {
        private const val POSITION = "position"
        private const val IMAGES_LIST = "images_list"

        fun newInstance(paths: ArrayList<String>, position: Int): ImageZoomPagerFragment {
            val bundle = bundleOf(
                    IMAGES_LIST to paths,
                    POSITION to position
            )
            val fragment = ImageZoomPagerFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    inner class ImageZoomPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int {
            return imagesViewModel.images.size
        }

        override fun createFragment(position: Int): Fragment {
            return ImageZoomFragment.newInstance(imagesViewModel.images[position])
        }
    }
}