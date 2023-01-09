package com.sopt.smeme.presentation.view.archive

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sopt.smeme.business.adaptor.ArchiveAdapter
import com.sopt.smeme.business.viewmodel.ArchiveViewModel
import com.sopt.smeme.databinding.FragmentArchiveBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
class ArchiveFragment @Inject constructor(
    @ApplicationContext context: Context
) : Fragment() {
    private val viewModel by viewModels<ArchiveViewModel>()
    private var _binding: FragmentArchiveBinding? = null
    private val binding: FragmentArchiveBinding
        get() = requireNotNull(_binding) { "value of _binding is null" }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentArchiveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ArchiveAdapter(requireContext())
        binding.rvExpressionArchive.adapter = adapter
        adapter.setArchiveList(viewModel.archiveList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}