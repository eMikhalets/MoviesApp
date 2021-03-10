package com.emikhalets.moviesapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.emikhalets.moviesapp.R
import com.emikhalets.moviesapp.databinding.FragmentPersonDetailsBinding
import com.emikhalets.moviesapp.model.pojo.Profile
import com.emikhalets.moviesapp.model.pojo.ResponsePersonId
import com.emikhalets.moviesapp.utils.PersonDetailsNavigation
import com.emikhalets.moviesapp.utils.buildProfile185px
import com.emikhalets.moviesapp.utils.formatImagesList
import com.emikhalets.moviesapp.view.adapters.ImageAdapter
import com.emikhalets.moviesapp.viewmodel.PersonDetailsViewModel

class PersonDetailsFragment : Fragment() {

    private var _binding: FragmentPersonDetailsBinding? = null
    private val binding get() = _binding!!

    private val navClickListener: PersonDetailsNavigation?
        get() = requireActivity() as? PersonDetailsNavigation?

    private var imagesAdapter: ImageAdapter? = null
    private val personDetailsViewModel: PersonDetailsViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerAdapters()
        if (savedInstanceState == null) {
            arguments?.let {
                val personId = it.getInt(PERSON_ID)
                personDetailsViewModel.loadPersonData(personId)
            }
        } else {
            binding.scrollMovie.scrollTo(personDetailsViewModel.scrollPos, personDetailsViewModel.scrollPos)
        }

        with(personDetailsViewModel) {
            person.observe(viewLifecycleOwner, { personData ->
                setData(personData)
                imagesAdapter?.submitList(personData.images.profiles)
            })
            uiVisibility.observe(viewLifecycleOwner, { setInterfaceVisibility(it) })
            notice.observe(viewLifecycleOwner, { msg ->
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        personDetailsViewModel.scrollPos = binding.scrollMovie.scrollY
        binding.listImages.adapter = null
        _binding = null
    }

    private fun initRecyclerAdapters() {
        imagesAdapter = ImageAdapter { list, pos -> onImageClick(list, pos) }
        binding.listImages.apply {
            adapter = imagesAdapter
            isNestedScrollingEnabled = false
        }
    }

    private fun onImageClick(path: String) {
        val list = formatImagesList(path)
        navClickListener?.navigateFromPersonDetailsToImageZoom(list, 0)
    }

    private fun onImageClick(snapshot: List<Profile>, position: Int) {
        val list = formatImagesList(snapshot)
        navClickListener?.navigateFromPersonDetailsToImageZoom(list, position)
    }

    private fun setData(data: ResponsePersonId) {
        with(binding) {
            data.profile_path?.let { img ->
                imagePerson.load(buildProfile185px(img)) { fallback(R.drawable.ph_actor) }
                imagePerson.setOnClickListener { onImageClick(img) }
            }
            textName.text = data.name
            textDepartment.text = data.known_for_department
            textBirthday.text = getString(
                    R.string.variable_birthday,
                    formatData(data.birthday)
            )
            textDeathday.text = getString(
                    R.string.variable_deathday,
                    formatData(data.deathday)
            )
            textPlaceBirth.text = getString(
                    R.string.variable_place_birth,
                    formatData(data.place_of_birth)
            )
            textBiographyContent.text = data.biography
        }
    }

    private fun formatData(data: String?): String {
        return data ?: "-"
    }

    private fun setInterfaceVisibility(bool: Boolean) {
        val duration = 500L
        with(binding) {
            pbLoadingData.animate().alpha(alpha(!bool)).setDuration(duration).start()
            imagePerson.animate().alpha(alpha(bool)).setDuration(duration).start()
            textName.animate().alpha(alpha(bool)).setDuration(duration).start()
            textDepartment.animate().alpha(alpha(bool)).setDuration(duration).start()
            textBirthday.animate().alpha(alpha(bool)).setDuration(duration).start()
            textDeathday.animate().alpha(alpha(bool)).setDuration(duration).start()
            textPlaceBirth.animate().alpha(alpha(bool)).setDuration(duration).start()
            textBiographyLabel.animate().alpha(alpha(bool)).setDuration(duration).start()
            textBiographyContent.animate().alpha(alpha(bool)).setDuration(duration).start()
            textImagesLabel.animate().alpha(alpha(bool)).setDuration(duration).start()
            listImages.animate().alpha(alpha(bool)).setDuration(duration).start()
        }
    }

    private fun alpha(isVisible: Boolean): Float {
        return if (isVisible) 1f else 0f
    }

    companion object {
        private const val PERSON_ID = "person_id"

        fun newInstance(personId: Int): PersonDetailsFragment {
            val bundle = bundleOf(PERSON_ID to personId)
            val fragment = PersonDetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}