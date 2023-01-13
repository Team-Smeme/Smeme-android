package com.sopt.smeme.bridge.controller.response

import com.sopt.smeme.DateUtil
import com.sopt.smeme.bridge.model.Archive
import kotlinx.serialization.Serializable

@Serializable
data class ArchiveData(
    val scraps: List<ScrapData>
) {
    @Serializable
    data class ScrapData(
        val id: Long,
        val paragraph: String,
        val createdAt: String
    ) : DateProvider {
        override fun createdAt() = DateUtil.asObject(createdAt)
    }

    fun asArchive() = scraps.map {
        Archive(it.id, it.paragraph)
    }
}