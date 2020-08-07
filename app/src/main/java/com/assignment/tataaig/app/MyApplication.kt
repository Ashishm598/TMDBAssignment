package com.assignment.tataaig.app

import android.app.Application

class MyApplication : Application() {

    companion object {
        const val LOG_TAG = "movieDB-tag"
    }

    override fun onCreate() {
        super.onCreate()
    }

}