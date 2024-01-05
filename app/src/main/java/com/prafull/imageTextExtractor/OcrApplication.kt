package com.prafull.imageTextExtractor

import android.app.Application
import com.prafull.imageTextExtractor.data.AppContainer
import com.prafull.imageTextExtractor.data.AppContainerImpl

class OcrApplication: Application() {

    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainerImpl(this)
    }
}