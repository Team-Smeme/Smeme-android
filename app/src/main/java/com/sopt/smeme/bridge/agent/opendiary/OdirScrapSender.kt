package com.sopt.smeme.bridge.agent.opendiary

import com.sopt.smeme.SmemeException
import com.sopt.smeme.bridge.controller.OdirDetailConnection
import com.sopt.smeme.bridge.controller.request.OdirScrapRequest
import com.sopt.smeme.bridge.controller.response.OdirScrapData
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject

@ViewModelScoped
class OdirScrapSender @Inject constructor(
    private val odirDetailConnection: OdirDetailConnection
) {
    suspend fun sendScrap(id: Int, paragraph: String) =
         CoroutineScope(Dispatchers.IO).async {
             val response = odirDetailConnection.postScrap(
                 OdirScrapRequest(
                     id, paragraph
                 )
             )
             if (!response.isSuccessful()) {
                 throw SmemeException(response.status, response.message)
             }

             OdirScrapData(response.data?.scrapId ?: -1)

         }.await()
}