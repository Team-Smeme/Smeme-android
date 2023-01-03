package com.sopt.smeme.presentation.view

import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.smeme.R
import com.sopt.smeme.business.viewmodel.ViewModelFrame
import com.sopt.smeme.databinding.ActivityTestBinding
import com.sopt.smeme.business.adaptor.TempAdaptor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestActivity : ViewBoundActivity<ActivityTestBinding>(R.layout.activity_test) {
    private val tempViewModel: ViewModelFrame by viewModels()
    private val tempAdaptor = TempAdaptor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun constructLayout() {
        // layout set by adaptor
    }

    override fun listen() {
        // do listen
    }

    override fun observe() {
        // viewModel observing
    }
}