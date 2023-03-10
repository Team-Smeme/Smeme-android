package com.sopt.smeme.presentation.view.home

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.sopt.smeme.DateUtil
import com.sopt.smeme.R
import com.sopt.smeme.SmemeException
import com.sopt.smeme.business.adaptor.MyDiaryAdaptor
import com.sopt.smeme.business.viewmodel.mydiary.MyDiaryProvider
import com.sopt.smeme.databinding.FragmentMyDiaryBinding
import com.sopt.smeme.presentation.view.mdir.MdirDetailActivity
import com.sopt.smeme.presentation.view.mdir.WriteDiaryForeignActivity
import com.sopt.smeme.presentation.view.mdir.WriteDiaryStep1Activity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ViewWithFragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@AndroidEntryPoint
class MyDiaryHomeFragment @Inject constructor(
    @ApplicationContext context: Context
) : Fragment() {
    private var _binding: FragmentMyDiaryBinding? = null
    private val binding get() = requireNotNull(_binding) { "Error exist on MyDiaryHomeFragment" }
    private var isFabOpen = false
    private var targetDate: LocalDateTime = LocalDateTime.now() // TODO

    private val myDiaryProvider: MyDiaryProvider by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyDiaryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MyDiaryAdaptor(::navigateToDetail, requireContext())
        binding.rvMyDiary.adapter = adapter

        // ?????? ????????? ?????? ?????? ?????? //
        targetDate = LocalDateTime.now() // TODO
        binding.txtDate.text = DateUtil.WithUser.asStringOnlyDate(targetDate)
        afterArrowAction()

        request()
        observe(adapter)
        listen()

    }

    fun listen() {
        setFabClickEvent()
        clickStep1()
        clickForeign()
        binding.icProfileMyDiary.setOnClickListener {
            Snackbar.make(binding.root, "??? ?????? ???????????? ?????? ????????? ???????????? ????????????.", Snackbar.LENGTH_SHORT).show()
        }
    }

    fun request() {
        request2Server()

        binding.icArrowLeft.setOnClickListener {
            targetDate = targetDate.minusDays(1)
            request2Server()
            afterArrowAction()
        }

        binding.icArrowRight.setOnClickListener {
            if (targetDate.isBefore(LocalDateTime.now())) {
                targetDate = targetDate.plusDays(1)
                request2Server()
                afterArrowAction()
            }
        }
    }

    private fun request2Server() {
        myDiaryProvider.requestGetList(
            targetDate.minusHours(9).toLocalDate(),
            onError = {
                if (it == null) {
                    Toast.makeText(context, "????????? ???????????????.", Toast.LENGTH_SHORT).show()
                } else {
                    when (it) {
                        is HttpException -> {
                            if (it.code() == 401) {
                                // TODO
                            }
                        }
                        is SmemeException -> {
                            if (it.status == 401) {
                                // TODO
                            }
                        }
                    }
                }
            }
        )
    }

    fun observe(myDiaryAdaptor: MyDiaryAdaptor) {
        myDiaryProvider.dairies.observe(viewLifecycleOwner) {
            myDiaryAdaptor.setDiaryList(it)
            checkMyDiaryExist(myDiaryAdaptor)
        }
    }

    private fun setFabClickEvent() {
        binding.fabPlus.setOnClickListener {
            toggleFab()
        }
        binding.fabX.setOnClickListener {
            toggleFab()
        }
    }

    private fun toggleFab() {
        with(binding) {
            if (isFabOpen) {
                ObjectAnimator.ofFloat(fabPlus, View.ROTATION, 45f, 0f).apply { start() }
                ObjectAnimator.ofFloat(fabX, View.ROTATION, 0f, 45f).apply { start() }
                fabPlus.visibility = View.VISIBLE
                fabX.visibility = View.INVISIBLE
                fabForeign.visibility = View.INVISIBLE
                fabKorean.visibility = View.INVISIBLE

                background.visibility = View.GONE
                (activity as HomeActivity).changeBackgroundColor("#00000000")
                (activity as HomeActivity).window.statusBarColor = Color.parseColor("#00000000")
                background.isEnabled = true
                (activity as HomeActivity).binding.viewBnv.visibility = View.GONE

            } else { // ????????? ?????? ?????? ??????
                ObjectAnimator.ofFloat(fabPlus, View.ROTATION, 0f, 45f).apply { start() }
                ObjectAnimator.ofFloat(fabX, View.ROTATION, 45f, 0f).apply { start() }
                fabPlus.visibility = View.INVISIBLE
                fabX.visibility = View.VISIBLE
                fabForeign.visibility = View.VISIBLE
                fabKorean.visibility = View.VISIBLE
                background.visibility = View.VISIBLE

                (activity as HomeActivity).window.statusBarColor = Color.parseColor("#4D000000")
                background.setOnClickListener(null)
                (activity as HomeActivity).binding.viewBnv.visibility = View.VISIBLE
                (activity as HomeActivity).binding.viewBnv.setOnClickListener(null)
            }

            isFabOpen = !isFabOpen
        }
    }


    private fun clickStep1() {
        binding.fabKorean.setOnClickListener {
            val toStep1 = Intent(context, WriteDiaryStep1Activity::class.java)
            startActivity(toStep1)
            onDestroy()
            onDestroyView()
            activity?.finish()
        }
    }

    private fun clickForeign() {
        binding.fabForeign.setOnClickListener {
            val toForeign = Intent(context, WriteDiaryForeignActivity::class.java)
            startActivity(toForeign)
            activity?.finish()
        }
    }


    private fun checkMyDiaryExist(adaptor: MyDiaryAdaptor) {
        if (adaptor.itemCount > 0) {
            binding.rvMyDiary.visibility = View.VISIBLE
            binding.txtNoDiary.visibility = View.GONE
        }
        else{
            binding.rvMyDiary.visibility = View.GONE
            binding.txtNoDiary.visibility = View.VISIBLE
        }
    }

    private fun afterArrowAction() {
        binding.txtDate.text = DateUtil.WithUser.asStringOnlyDate(targetDate)
        if (targetDate.isEqual(LocalDateTime.now())) {
            binding.icArrowRight.setImageResource(R.drawable.ic_arrow_right_inactive)
        } else if (targetDate.isBefore(LocalDateTime.now())) {
            binding.icArrowRight.setImageResource(R.drawable.ic_arrow_right)
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun navigateToDetail(id: Long) {
        val intent = Intent(requireContext(), MdirDetailActivity::class.java).apply {
            putExtra("diaryId", id)
        }
        startActivity(intent)
        activity?.finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


