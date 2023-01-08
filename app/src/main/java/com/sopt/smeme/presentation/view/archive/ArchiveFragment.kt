package com.sopt.smeme.presentation.view.archive

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sopt.smeme.databinding.FragmentArchiveBinding

class ArchiveFragment : Fragment() {
    private var _binding: FragmentArchiveBinding? = null
    private val binding: FragmentArchiveBinding
        get() = requireNotNull(_binding) { "value of _binding is null" }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentArchiveBinding.inflate(inflater, container, false)
        return binding.root
    }
}