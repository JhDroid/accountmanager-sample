package com.jhdroid.accountmanager

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jhdroid.accountmanager.authenticator.AuthenticatorActivity
import com.jhdroid.accountmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupView()
    }

    private fun setupView() {
        binding.mainMyAccountBtn.setOnClickListener {
            AccountHelper.getMyAccounts()?.forEach { account ->
                appendLog("name : ${account.name}\ntype : ${account.type}")
            } ?: appendLog("계정 정보 없음")
        }

        binding.mainAllAccountBtn.setOnClickListener {
            AccountHelper.getAccounts()?.forEach { account ->
                appendLog("name : ${account.name}\ntype : ${account.type}")
            } ?: appendLog("계정 정보 없음")
        }

        binding.mainLoginBtn.setOnClickListener {
            startActivityForResult(
                Intent(this, AuthenticatorActivity::class.java),
                AuthenticatorActivity.LOGIN_REQUEST_CODE
            )
        }

        binding.mainRemoveAccountBtn.setOnClickListener {
            AccountHelper.getMyAccounts()?.forEach { account ->
                appendLog("remove account name : ${account.name}")
                AccountHelper.removeAccount(account)
            }
        }

        binding.mainGetTokenBtn.setOnClickListener {
            AccountHelper.getMyAccounts()?.forEach { account ->
                val token = AccountHelper.getAuthToken(this, account)
                appendLog("authToken account name : ${account.name}\ntoken : token")

            }
        }
    }

    private fun appendLog(msg: String) {
        binding.mainAccountInfoTv.append("$msg\n\n")
    }
}