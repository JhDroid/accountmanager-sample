package com.jhdroid.accountmanager.authenticator

import android.app.Service
import android.content.Intent
import android.os.IBinder

class AuthenticatorService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        val authenticator = Authenticator(this)
        return authenticator.iBinder
    }
}