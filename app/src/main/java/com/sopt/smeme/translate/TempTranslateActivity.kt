package com.sopt.smeme.translate

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sopt.smeme.databinding.ActivityTempTranslateBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TempTranslateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTempTranslateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTempTranslateBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val api = NaverAPI.create()
        binding.btnTranslate.setOnClickListener {
            api.transferPapago("ko", "en", binding.etDiary.text.toString())
                .enqueue(object : Callback<ResultTransferPapago> {
                    override fun onResponse(
                        call: Call<ResultTransferPapago>,
                        response: Response<ResultTransferPapago>
                    ) {
                        Log.d("성공", "성공 : ${response.raw()}")
                        binding.etDiary.setText(response.body()?.message?.result?.translatedText)
                    }

                    override fun onFailure(call: Call<ResultTransferPapago>, t: Throwable) {
                        Log.d("실패", "실패 : $t")
                    }
                })
        }

    }
}

