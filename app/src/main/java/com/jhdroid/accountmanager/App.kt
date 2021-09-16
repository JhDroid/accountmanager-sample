package com.jhdroid.accountmanager

import android.app.Application

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        /**
         * AccountManager 객체 생성
         * */
        AccountHelper.initAccountManager(this)
    }
}