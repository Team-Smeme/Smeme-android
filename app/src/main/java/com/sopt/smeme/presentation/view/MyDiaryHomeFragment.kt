package com.sopt.smeme.presentation.view

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.sopt.smeme.bridge.model.MyDiary
import com.sopt.smeme.business.adaptor.MyDiaryAdaptor
import com.sopt.smeme.databinding.FragmentMyDiaryBinding

class MyDiaryHomeFragment : Fragment() {
    private var _binding: FragmentMyDiaryBinding? = null
    private val binding get() = requireNotNull(_binding) { "Error exist on MyDiaryHomeFragment" }

    private var isFabOpen = false


    private val mockDiaryList = listOf<MyDiary>(
        MyDiary(
            myDiaryTime = "01:19",
            myDiary = "I watched Avatar with my boyfriend at Hongdae CGV. I should have skimmed the previous season - Avatar1.. I really couldn’t get what they were saying and the universe(??). What I was annoyed then was 두팔 didn’t know that as me. I think 두팔 who is my boyfriend should study before wathcing…. but Avatar2 is amazing movie I think. In my personal opinion, the jjin main character of Avatar2 is not Sully, but his son. Go away Sully ㅋㅋ. I didn’t know how time flew. Byebye after drinking cocktail together. These days I am so so happy to see him more often. I don’t kno w if I am writing properly or not but I am just studying very hard. "
        ),
        MyDiary(
            myDiaryTime = "12:24",
            myDiary = "Sometimes I feel a great deal of responsibility when I teach students. Although it is kind of private tutoring - I mean it is not the public sth, the fact that the students expect me the role of teacher makes me feel suppressed.\n" +
                    "It is true that I am trying my best to 전달하다 the right direction to my students"
        ),
        MyDiary(
            myDiaryTime = "22:14",
            myDiary = "I watched Avatar with my boyfriend at Hongdae CGV. I should have skimmed the previous season - Avatar1.. I really couldn’t get what they were saying and the universe(??). What I was annoyed then was 두팔 didn’t know that as me. I think 두팔 who is my boyfriend should study before wathcing…. but Avatar2 is amazing movie I think. In my personal opinion, the jjin main character of Avatar2 is not Sully, but his son. Go away Sully ㅋㅋ. I didn’t know how time flew. Byebye after drinking cocktail together. These days I am so so happy to see him more often. I don’t kno w if I am writing properly or not but I am just studying very hard. "
        ),

        )

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
        val adapter = MyDiaryAdaptor(requireContext())
        binding.rvMyDiary.adapter = adapter
        adapter.setDiaryList(mockDiaryList)
        checkMyDiaryExist()
        setFabClickEvent()
        clickStep1()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

            } else { // 플로팅 액션 버튼 열기 - 닫혀있는 플로팅 버튼 꺼내는 애니메이션
                ObjectAnimator.ofFloat(fabPlus, View.ROTATION, 0f, 45f).apply { start() }
                ObjectAnimator.ofFloat(fabX, View.ROTATION, 45f, 0f).apply { start() }
                fabPlus.visibility = View.INVISIBLE
                fabX.visibility = View.VISIBLE
                fabForeign.visibility = View.VISIBLE
                fabKorean.visibility = View.VISIBLE

            }
            isFabOpen = !isFabOpen
        }

    }

    private fun clickStep1() {
        binding.fabKorean.setOnClickListener {
            val toStep1 = Intent(context, WriteDiaryKoreanActivity::class.java)
            startActivity(toStep1)
        }
    }

    private fun checkMyDiaryExist() {
        if (mockDiaryList.size >= 0) {
            binding.rvMyDiary.visibility = View.VISIBLE
        }
    }

}