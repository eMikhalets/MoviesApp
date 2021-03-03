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
import com.emikhalets.moviesapp.utils.PersonListNavigation
import com.emikhalets.moviesapp.view.adapters.CastAdapter
import com.emikhalets.moviesapp.view.adapters.CrewAdapter
import com.emikhalets.moviesapp.viewmodel.CastPagerViewModel

class CastListFragment : Fragment() {

    private var _binding: FragmentCastListBinding? = null
    private val binding get() = _binding!!

    private val navClickListener: PersonListNavigation?
        get() = requireActivity() as? PersonListNavigation?

    private var castAdapter: CastAdapter? = null
    private var crewAdapter: CrewAdapter? = null
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

        val imageCornerRadius = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                2f,
                resources.displayMetrics
        )
        arguments?.let {
            val castType = it.getInt(CAST_TYPE) ?: 0
            if (castType == 0) initCastAdapter(imageCornerRadius)
            else initCrewAdapter(imageCornerRadius)
        }
        castPagerViewModel.notice.observe(viewLifecycleOwner, { msg ->
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.listCast.adapter = null
        _binding = null
    }

    private fun initCastAdapter(imageCornerRadius: Float) {
        castAdapter = CastAdapter(imageCornerRadius) { onPersonClick(it) }
        with(binding) {
            listCast.apply {
                setHasFixedSize(true)
                adapter = castAdapter
            }
        }
        castPagerViewModel.cast.observe(viewLifecycleOwner, { list ->
            castAdapter?.submitList(list)
        })
    }

    private fun initCrewAdapter(imageCornerRadius: Float) {
        crewAdapter = CrewAdapter(imageCornerRadius) { onPersonClick(it) }
        with(binding) {
            listCast.apply {
                setHasFixedSize(true)
                adapter = crewAdapter
            }
        }
        castPagerViewModel.crew.observe(viewLifecycleOwner, { list ->
            crewAdapter?.submitList(list)
        })
    }

    private fun onPersonClick(personId: Int) {
        navClickListener?.navigateFromPersonListToPersonDetails(personId)
    }

    companion object {
        private const val CAST_TYPE = "cast_type"

        fun newInstance(castType: Int): CastListFragment {
            val bundle = bundleOf(CAST_TYPE to castType)
            val fragment = CastListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}