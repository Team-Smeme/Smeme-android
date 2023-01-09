package com.sopt.smeme.bridge.agent.mydiary

import com.sopt.smeme.bridge.controller.response.DataResponse
import com.sopt.smeme.bridge.controller.response.MyDiaryData
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class MockMyDiaryRetriever @Inject constructor() : MyDiaryRetrieving {
    override suspend fun getList() =
        DataResponse(
            200, true, "success",
            listOf(
                mock(
                    1L,
                    "I watched Avatar with my boyfriend at Hongdae CGV. I should have skimmed the previous season - Avatar1.. I really couldn’t get what they were saying and the universe(??). What I was annoyed then was 두팔 didn’t know that as me. I think 두팔 who is my boyfriend should study before wathcing…. but Avatar2 is amazing movie I think. In my personal opinion, the jjin main character of Avatar2 is not Sully, but his son. Go away Sully ㅋㅋ. I didn’t know how time flew. Byebye after drinking cocktail together. These days I am so so happy to see him more often. I don’t kno w if I am writing properly or not but I am just studying very hard. ",
                    "2023-01-07 22:30",
                    true,
                    30
                ),
                mock(
                    2L,
                    "Sometimes I feel a great deal of responsibility when I teach students. Although it is kind of private tutoring - I mean it is not the public sth, the fact that the students expect me the role of teacher makes me feel suppressed.\nIt is true that I am trying my best to 전달하다 the right direction to my students",
                    "2023-01-08 10:40",
                    true,
                    1
                ),
                mock(
                    4L,
                    "I watched Avatar with my boyfriend at Hongdae CGV. I should have skimmed the previous season - Avatar1.. I really couldn’t get what they were saying and the universe(??). What I was annoyed then was 두팔 didn’t know that as me. I think 두팔 who is my boyfriend should study before wathcing…. but Avatar2 is amazing movie I think. In my personal opinion, the jjin main character of Avatar2 is not Sully, but his son. Go away Sully ㅋㅋ. I didn’t know how time flew. Byebye after drinking cocktail together. These days I am so so happy to see him more often. I don’t kno w if I am writing properly or not but I am just studying very hard.",
                    "2022-12-30 18:40",
                    true,
                    99
                )
            )
        )

    private fun mock(
        id: Long,
        content: String,
        createdAt: String,
        isPublic: Boolean,
        likes: Long
    ) =
        MyDiaryData(id, content, createdAt, isPublic, likes)
}