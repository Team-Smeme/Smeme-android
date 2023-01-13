package com.sopt.smeme.bridge.agent.mydiary

import com.sopt.smeme.bridge.model.MyDiary
import dagger.hilt.android.scopes.ViewModelScoped
import java.time.LocalDate
import javax.inject.Inject

@ViewModelScoped
class MockMyDiaryRetriever @Inject constructor() : MyDiaryRetrieving {
    override suspend fun getList(date: LocalDate) =
        listOf(
            mock(85,
                "I watched Avatar with my boyfriend at Hongdae CGV. I should have skimmed the previous season - Avatar1.. I really couldn’t get what they were saying and the universe(??). What I was annoyed then was 두팔 didn’t know that as me. I think 두팔 who is my boyfriend should study before wathcing…. but Avatar2 is amazing movie I think. In my personal opinion, the jjin main character of Avatar2 is not Sully, but his son. Go away Sully ㅋㅋ. I didn’t know how time flew. Byebye after drinking cocktail together. These days I am so so happy to see him more often. I don’t kno w if I am writing properly or not but I am just studying very hard. ",
                "22:30"
            ),
            mock(84,
                "Sometimes I feel a great deal of responsibility when I teach students. Although it is kind of private tutoring - I mean it is not the public sth, the fact that the students expect me the role of teacher makes me feel suppressed.\nIt is true that I am trying my best to 전달하다 the right direction to my students",
                "10:40"
            ),
            mock(83,
                "I watched Avatar with my boyfriend at Hongdae CGV. I should have skimmed the previous season - Avatar1.. I really couldn’t get what they were saying and the universe(??). What I was annoyed then was 두팔 didn’t know that as me. I think 두팔 who is my boyfriend should study before wathcing…. but Avatar2 is amazing movie I think. In my personal opinion, the jjin main character of Avatar2 is not Sully, but his son. Go away Sully ㅋㅋ. I didn’t know how time flew. Byebye after drinking cocktail together. These days I am so so happy to see him more often. I don’t kno w if I am writing properly or not but I am just studying very hard.",
                "18:40"
            )
        )

    private fun mock(
        id: Long,
        content: String,
        time: String
    ) = MyDiary(id, time, content)

}