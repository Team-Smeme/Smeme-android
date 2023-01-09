package com.sopt.smeme.business.viewmodel

import androidx.lifecycle.ViewModel
import com.sopt.smeme.bridge.model.Archive

class ArchiveViewModel : ViewModel() {
    val archiveList = listOf (
        Archive("Luck favors the prepared"),
        Archive("resilient"),
        Archive("it's my reason for living"),
    )
}