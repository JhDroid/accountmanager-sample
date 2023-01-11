package com.jhdroid.accountmanager

import android.accounts.AccountManager
import android.accounts.AccountManagerCallback
import android.accounts.AccountManagerFuture
import android.os.Bundle
import timber.log.Timber

/**
 * Bundle이 포함된 AccountManagerFuture
 * run()을 호출, 호출이 성공하면 Bundle(AccountManagerFuture<Bundle>)에서 토큰을 가져와야 함
 *
 * 토큰 요청은 다음과 같은 이유로 실패할 수 있음
 * 1. 기기나 네트워크 오류
 * 2. 사용자가 앱에 계정 액세스 권한 부여하지 않음
 * - 위 두 사례는 사용자에게 오류 메시지를 표시해 처리하면 됨
 *
 * 3. 저장된 계정 사용자 인증 정보가 충분하지 않아 계정 액세스 권한을 얻지 못함
 * - AccountManagerCallback(OnTokenAcquired)에서 수신한 Bundle 통해 전달됨
 * - 사용자가 처음 로그인했거나 계정이 만료되어 재로그인이 필요하거나 사용자 인증 정보가 잘못된 상태일 수 있음
 * - 유효한 토큰을 원한다면 Intent를 실행해 사용자 정보를 받아와야함 (Authenticator 클래스 참고)
 *
 * 4. 캐시된 인증 토큰 만료
 * - 정상적인 앱이라면 이런 실패는 자동으로 처리해야 함 ^^
 * */
class OnTokenAcquired : AccountManagerCallback<Bundle> {
    override fun run(result: AccountManagerFuture<Bundle>?) {
        Timber.d("OpenTokenAcquired run()")

        val bundle = result?.result

        // 호출이 성공했다면 토큰을 가져옴
        val token = bundle?.getString(AccountManager.KEY_AUTHTOKEN)

        Timber.d("User token : $token")
    }
}