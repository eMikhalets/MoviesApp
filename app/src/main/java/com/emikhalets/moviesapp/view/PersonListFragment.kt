package com.emikhalets.moviesapp.view

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.emikhalets.moviesapp.databinding.FragmentPersonListBinding
import com.emikhalets.moviesapp.utils.PersonListNavigation
import com.emikhalets.moviesapp.view.adapters.PopPersonListAdapter
import com.emikhalets.moviesapp.viewmodel.PersonListViewModel

class PersonListFragment : Fragment() {

    private var _binding: FragmentPersonListBinding? = null
    private val binding get() = _binding!!

    private val navClickListener: PersonListNavigation?
        get() = requireActivity() as? PersonListNavigation?

    private var personAdapter: PopPersonListAdapter? = null
    private val popPersonViewModel: PersonListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerAdapter()
        if (savedInstanceState == null) {
            popPersonViewModel.initPager()
            setInterfaceVisibility(false)
        }
        popPersonViewModel.person.observe(viewLifecycleOwner) { list ->
            personAdapter?.submitList(list)
            setInterfaceVisibility(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.listPerson.adapter = null
        _binding = null
    }

    private fun initRecyclerAdapter() {
        val imageCornerRadius = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            2f,
            resources.displayMetrics
        )
        personAdapter = PopPersonListAdapter(imageCornerRadius) { onCastClickClick(it) }
        binding.listPerson.apply {
            setHasFixedSize(true)
            adapter = personAdapter
        }
    }

    private fun onCastClickClick(personId: Int) {
        navClickListener?.navigateFromPersonListToPersonDetails(personId)
    }

    private fun setInterfaceVisibility(bool: Boolean) {
        val duration = 500L
        with(binding) {
            pbLoadingData.animate().alpha(alpha(!bool)).setDuration(duration).start()
            listPerson.animate().alpha(alpha(bool)).setDuration(duration).start()
        }
    }

    private fun alpha(isVisible: Boolean): Float {
        return if (isVisible) 1f else 0f
    }

    companion object {
        fun newInstance(): PersonListFragment {
            return PersonListFragment()
        }
    }
}