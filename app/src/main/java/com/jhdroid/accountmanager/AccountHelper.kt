package com.jhdroid.accountmanager

import android.accounts.Account
import android.accounts.AccountManager
import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle

object AccountHelper {
    private var accountManager: AccountManager? = null

    /**
     * AccountManager에 계정 이름을 쿼리할 때 계정 유형별로 필터링 가능
     * 계정 유형은 계정을 발급한 주체를 고유하게 식별하는 문자열
     * Google의 계정 유형은 'com.google', Twitter의 계정 유형은 'com.twitter.android.auht.login'
     * */
    private const val MY_ACCOUNT_TYPE = "com.jhdroid.auth.login"

    fun initAccountManager(context: Context) {
        accountManager = AccountManager.get(context)
    }

    fun getAccountManager(): AccountManager? = accountManager

    /**
     * 사용자 계정 목록을 가져옴
     * 해당 API에서 개인 정보 및 민감한 사용자 데이터를 반환하므로 이를 사용자에게 명확하게 공개해야함
     * @Account Account 객체 자체는 데이터를 보호하지 않으며 사용자 계정 이름 이외의 다른 항목에 액세스할 권한을 부여하지 않음
     * */
    fun getMyAccounts(): Array<out Account>? = accountManager?.getAccountsByType(MY_ACCOUNT_TYPE)

    /**
     * 접근 가능한 모든 계정 목록을 불러옴
     * */
    fun getAccounts(): Array<out Account>? = accountManager?.accounts
    
    
    /**
     * 맟춤 계정 유형 생성
     * 1. 사용자로부터 사용자 인증 정보 수집 (id & pw, 생체 인식 스캔 등)
     * 2. 서버에서 사용자 인증 정보 인증
     * 3. 기기에 사용자 인증 정보 저장
     *
     * 올바른 처리를 위해 Android 프레임워크는 AccountAuthenticatorActivity를 제공하며 이 클래스를 확장해 자체 맞춤 인증자 생성 가능
     * 사용자 인증 정보 수집과 인증 처리 방법은 개발자가 알아서..
     *
     * AccountManager는 암호화 서비스나 키체인이 아님, 개발자가 전달한 그대로 사용자 인증 정보를 '일반 텍스트'로 저장
     * 루트에서만 액세스 가능한 DB에 저장하기 때문에 대부분의 기기에서는 우려할 사안이 아님
     * 단, 루팅된 기기(adb 액세스 권한이 있는 기기)에서는 누구나 사용자 인증 정보를 읽을 수 있음
     * 이점을 고려해 사용자의 실제 비밀번호를 addAccountExplicitly()에 전달해서는 안됨
     * 사용이 제한적인 암호학적으로 안전한 토큰을 저장
     * */
    fun addAccount(id: String, pw: String, authToken: String? = "") {
        Account(id, MY_ACCOUNT_TYPE).also { account ->
            accountManager?.addAccountExplicitly(account, pw, null)

            if (!authToken.isNullOrEmpty()) {
                accountManager?.setAuthToken(account, account.type, authToken)
            }
        }
    }

    /**
     * 사용자 인증 토큰 설정
     * */
    fun setAuthToken(account: Account?, authToken: String?) {
        if (account != null && !authToken.isNullOrEmpty()) {
            accountManager?.setAuthToken(account, account.type, authToken)
        }
    }

    /**
     * 등록된 계정 제거
     * */
    fun removeAccount(account: Account?) {
        if (account != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                accountManager?.removeAccountExplicitly(account)
            } else {
                accountManager?.removeAccount(account, null, null)
            }
        }
    }

    /**
     * 인증 토큰 요청하기
     * */
    fun getAuthToken(activity: Activity, account: Account?) : String? {
        if (account != null) {
            val token = accountManager?.peekAuthToken(account, account.type)

            if (!token.isNullOrEmpty()) {
                return token
            } else {
                val feature = accountManager?.getAuthToken(
                    account,
                    account.type,
                    Bundle(),
                    activity,
                    OnTokenAcquired(),
                    null
                )

                return if (feature?.isDone == true) {
                    feature.result?.getString(AccountManager.KEY_AUTHTOKEN)
                } else {
                    ""
                }
            }
        } else {
            return ""
        }
    }

    /**
     * 계정 비밀번호 확인
     * */
    fun getPassword(account: Account?): String? {
        return try {
            accountManager?.getPassword(account)
        } catch (e: Exception) {
            ""
        }
    }
}