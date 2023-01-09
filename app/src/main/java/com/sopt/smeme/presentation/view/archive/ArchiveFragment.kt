package com.sopt.smeme.presentation.view.archive

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.sopt.smeme.business.adaptor.ArchiveAdapter
import com.sopt.smeme.business.viewmodel.ArchiveViewModel
import com.sopt.smeme.databinding.FragmentArchiveBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
class ArchiveFragment @Inject constructor(
    @ApplicationContext context: Context,
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

        constructLayout()
        listen()
    }

    private fun constructLayout() {
        val adapter = ArchiveAdapter(requireContext())
        binding.rvExpressionArchive.adapter = adapter
        adapter.setArchiveList(viewModel.archiveList)
    }

    private fun listen() {
        binding.tvDiaryTitleArchive.setOnClickListener {
            Snackbar.make(binding.root, "더 나은 서비스를 위해 페이지 준비중에 있습니다.", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}