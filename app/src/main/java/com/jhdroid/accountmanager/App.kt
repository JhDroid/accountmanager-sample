package com.jhdroid.accountmanager

import android.app.Application
import timber.log.Timber

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        /**
         * AccountManager 객체 생성
         * */
        AccountHelper.initAccountManager(this)
    }
}