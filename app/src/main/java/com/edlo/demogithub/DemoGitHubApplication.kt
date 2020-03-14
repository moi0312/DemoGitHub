package com.edlo.demogithub

import android.app.Application

class DemoGitHubApplication: Application() {
    companion object {
        lateinit var INSTANCE: DemoGitHubApplication
            private set
    }
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

}