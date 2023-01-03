package com.sopt.smeme

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Smeme : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}