package com.jhdroid.accountmanager.authenticator

//import android.accounts.AccountAuthenticatorActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jhdroid.accountmanager.AccountHelper
import com.jhdroid.accountmanager.databinding.ActivityLoginBinding

/**
 * 예제는 이것으로 구현했는데 Deprecated .........!
 * https://developer.android.com/reference/android/accounts/AccountAuthenticatorActivity?hl=ko
 * AppCompat 호환이 안된다고 해서 그냥 AppCompat으로 구현
 * */
//: AccountAuthenticatorActivity()
class AuthenticatorActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        /**
         * 사용자 인증 정보 수집
         * */
        binding.loginSubmitBtn.setOnClickListener {
            val accountId = binding.loginIdEdt.text.toString()
            val accountPw = binding.loginPwEdt.text.toString()

            AccountHelper.addAccount(accountId, accountPw)
            setResult(RESULT_OK)
            finish()
        }
    }
}