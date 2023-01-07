package com.sopt.smeme.presentation.view.odir

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sopt.smeme.R
import com.sopt.smeme.databinding.ActivityOdirDetailBinding

class OdirDetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityOdirDetailBinding.inflate(layoutInflater) }

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnHintOdirDetail.setOnClickListener {
            if (binding.btnHintOdirDetail.isChecked) {
                binding.btnHintOdirDetail.setTextColor(R.color.primary)
            } else {
                binding.btnHintOdirDetail.setTextColor(R.color.grey_400)
            }
        }
    }
}