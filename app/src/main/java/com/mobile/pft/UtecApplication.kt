package com.mobile.pft

import android.app.Application
import com.mobile.pft.data.ApiContainer
import com.mobile.pft.data.AppContainer

class UtecApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = ApiContainer()
    }
}