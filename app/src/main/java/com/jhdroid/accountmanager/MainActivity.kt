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
            startActivity(Intent(this, AuthenticatorActivity::class.java))
        }

        binding.mainRemoveAccountBtn.setOnClickListener {
            AccountHelper.removeAccount()
        }

        binding.mainGetTokenBtn.setOnClickListener {
            AccountHelper.getAuthToken(this)
        }
    }

    private fun appendLog(msg: String) {
        binding.mainAccountInfoTv.append("$msg\n\n")
    }
}