package com.shohjahon.notesapp.presentation.ui.home

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import com.shohjahon.notesapp.R
import com.shohjahon.notesapp.databinding.FragmentHomeBinding
import com.shohjahon.notesapp.presentation.adapters.HomeAdapter
import com.shohjahon.notesapp.presentation.adapters.ItemCallBack
import com.shohjahon.notesapp.presentation.dialog.BaseDialog
import com.shohjahon.notesapp.util.MyTextWatcher
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class HomeFragment : Fragment(), ItemCallBack, MyTextWatcher {

    private var _binding: FragmentHomeBinding? = null

    @Inject
    lateinit var homeAdapter: HomeAdapter

    val homeViewModel: HomeViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listHome.adapter = homeAdapter
        homeAdapter.itemCallBack = this
        binding.listHome.itemAnimator = DefaultItemAnimator()
        binding.search.addTextChangedListener(this)
        homeViewModel.getAllNotes()
        getAll()
        search()

        binding.add.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_NoteFragment)
        }
    }

    override fun afterTextChanged(s: Editable?) {
        homeViewModel.search("$s")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getAll() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            homeViewModel.notes.collectLatest {
                homeAdapter.setData(it)
                val count = it.size
                binding.count.text = if (count == 0) "Empty" else if (count == 1) "1 note" else "$count notes"
            }
        }
    }

    private fun search() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            homeViewModel.searchData.collectLatest {
                homeAdapter.setData(it)
                val count = it.size
                binding.count.text = if (count == 0) "Empty" else if (count == 1) "1 note" else "$count notes"
            }
        }
    }

    override fun onClicked(time: Long) {
        findNavController().navigate(R.id.action_HomeFragment_to_NoteFragment, bundleOf(Pair("time", time)))
    }

    override fun onLongClicked(time: Long, position: Int) {
        BaseDialog(requireContext()).setYesListener {
            homeViewModel.remove(time)
            it.dismiss()
            homeAdapter.notifyItemRemoved(position)
        }.show()
    }
}