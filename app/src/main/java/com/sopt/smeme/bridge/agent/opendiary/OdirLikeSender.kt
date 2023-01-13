package com.sopt.smeme.bridge.agent.opendiary

import com.sopt.smeme.SmemeException
import com.sopt.smeme.bridge.controller.OdirDetailConnection
import com.sopt.smeme.bridge.controller.request.OdirLikeRequest
import com.sopt.smeme.bridge.controller.response.OdirLikeData
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@ViewModelScoped
class OdirLikeSender @Inject constructor(
    private val odirDetailConnection: OdirDetailConnection,
) {
    suspend fun sendLike(id: Int) =
        CoroutineScope(Dispatchers.IO).async {
            val response = odirDetailConnection.postLike(
                OdirLikeRequest(id)
            )
            if (!response.isSuccessful()) {
                throw SmemeException(response.status, response.message)
            }
            OdirLikeData(response.data?.hasLike ?: false)

        }.await()
}