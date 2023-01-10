package com.sopt.smeme.business.viewmodel

import androidx.lifecycle.ViewModel
import com.sopt.smeme.bridge.model.OpenDiary

class OdirViewModel : ViewModel() {
    val odirList = listOf (
        OpenDiary("유저스님",1,"I don't really like being alone.. But sometimes I think I need some alone time. Time alone for me is time for relaxation. "),
        OpenDiary("자칭정해인", 2, "I think my day cycle changed a lot these days. I slept at 3 a.m. to do some works. I woke up at 10 a.m. In September, when I had just come back from Germany, I had an ideal daily routine. I just went to bed before 12, and woke  up at 8-9 am. I really want to change it."),
        OpenDiary("주니보이", 3, "My 동네 is Dongtan and since my town is new town so not many people are there. \uD83D\uDE07 Only cars and babies are there. What I want to say is that my town is not that cute and vibrant. Everything is made with well-structured city planning and people are just following the rule. However, I love the park in front of my apartment. There is huge park having -also-huge tree. It is so huge that I cannot take the selfie with the whole tree."),
        OpenDiary("최강찬미", 4, "Nothing special, I just walk. I try to walk more than 30 minutes everyday. Thinking that it is not that far I just avoid taking a bus. I think it is a good habit because I can also save my money.")
    )
}