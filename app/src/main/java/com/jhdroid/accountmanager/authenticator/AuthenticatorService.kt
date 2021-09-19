package com.jhdroid.accountmanager.authenticator

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * 인증자 서비스
 * 인증자는 여러 애플리케이션에서 사용할 수 있어야 하고 백그라운드에서 작동해야 하므로 Service내에서 실행해야 하며 이를 '인증자 서비스'라고 함
 * 인증자 서비스는 AbstractAccountAuthenticator를 확장한 클래스(Authenticator)의 getIBinder()를 리턴해주면 된다.
 * */
class AuthenticatorService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        val authenticator = Authenticator(this)
        return authenticator.iBinder
    }
}