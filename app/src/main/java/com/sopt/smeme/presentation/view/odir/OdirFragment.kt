package com.sopt.smeme.presentation.view.odir

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sopt.smeme.R
import com.sopt.smeme.databinding.FragmentOdirBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
class OdirFragment @Inject constructor(
    @ApplicationContext context: Context
) : Fragment() {
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

        constructLayout()
        listen()
        observe()
    }

    fun constructLayout() {

    }
    fun listen() {

    }
    fun observe() {

    }
}