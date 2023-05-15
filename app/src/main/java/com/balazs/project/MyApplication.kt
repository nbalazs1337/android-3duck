package com.balazs.project

import AppComponent
import AppModule
import android.app.Application
import com.balazs.project.presentation.HomeActivity


class MyApplication : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        // Perform any initialization tasks here
        // Example: Initialize logging, analytics, or other libraries

        // Initialize Dagger component
        appComponent.inject(WorkerFragment())
    }
}