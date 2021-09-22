package com.jhdroid.accountmanager.authenticator

//import android.accounts.AccountAuthenticatorActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import com.jhdroid.accountmanager.AccountHelper
import com.jhdroid.accountmanager.databinding.ActivityLoginBinding
import com.jhdroid.accountmanager.remote.ApiUtil
import com.jhdroid.accountmanager.remote.model.Auth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

/**
 * 예제는 이것으로 구현했는데 Deprecated .........!
 * https://developer.android.com/reference/android/accounts/AccountAuthenticatorActivity?hl=ko
 * AppCompat 호환이 안된다고 해서 그냥 AppCompat으로 구현
 * */
//: AccountAuthenticatorActivity()
class AuthenticatorActivity : AppCompatActivity() {
    companion object {
        const val LOGIN_REQUEST_CODE = 1234
    }

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        /**
         * 사용자 인증 정보 수집
         * */
        binding.loginSubmitBtn.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val accountId = binding.loginIdEdt.text.toString()
        val accountPw = binding.loginPwEdt.text.toString()

        val body = JsonObject().apply {
            addProperty("id", accountId)
            addProperty("pw", accountPw)
        }

        /**
         * 사용자 확인 후 인증 토큰도 Account 객체에 등록
         * ID, PW는 일반 텍스트로 저장되므로 꼭 암호화 과정을 거칠것
         * */
        ApiUtil.getApiService().login(body).enqueue(object : Callback<Auth> {
            override fun onResponse(call: Call<Auth>, response: Response<Auth>) {
                if (response.isSuccessful) {
                    val auth = response.body()
                    AccountHelper.addAccount(accountId, accountPw, auth?.authToken)

                    setResult(RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(this@AuthenticatorActivity,
                        "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Auth>, t: Throwable) {
                Timber.d("login error : ${t.localizedMessage}")

                Toast.makeText(this@AuthenticatorActivity,
                    "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}