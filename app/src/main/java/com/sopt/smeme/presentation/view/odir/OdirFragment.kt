package com.sopt.smeme.presentation.view.odir

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sopt.smeme.business.adaptor.OdirAdapter
import com.sopt.smeme.business.viewmodel.OdirViewModel
import com.sopt.smeme.databinding.FragmentOdirBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
class OdirFragment @Inject constructor(
    @ApplicationContext context: Context,
) : Fragment() {
    private val viewModel by viewModels<OdirViewModel>()
    private var _binding: FragmentOdirBinding? = null
    private val binding: FragmentOdirBinding
        get() = requireNotNull(_binding) { "value of _binding is null" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentOdirBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = OdirAdapter(requireContext())
        binding.rvDiaryOdir.adapter = adapter
        adapter.setOdirList(viewModel.odirList)

        binding.chipAllOdir.isChecked = true

        constructLayout()
        listen()
        observe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun constructLayout() {

    }

    fun listen() {

    }

    fun observe() {

    }
}