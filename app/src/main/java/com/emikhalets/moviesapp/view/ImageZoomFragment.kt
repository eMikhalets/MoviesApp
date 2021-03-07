package com.emikhalets.moviesapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import coil.load
import com.emikhalets.moviesapp.R
import com.emikhalets.moviesapp.databinding.FragmentImageZoomBinding
import com.emikhalets.moviesapp.utils.buildPoster780px

class ImageZoomFragment : Fragment() {

    private var _binding: FragmentImageZoomBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageZoomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var path = ""
        arguments?.let { path = it.getString(IMAGE, "") }
        binding.imageItem.load(buildPoster780px(path)) {
            placeholder(R.drawable.ph_image)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val IMAGE = "image"

        fun newInstance(path: String): ImageZoomFragment {
            val bundle = bundleOf(IMAGE to path)
            val fragment = ImageZoomFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}