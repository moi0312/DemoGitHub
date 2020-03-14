package com.edlo.demogithub.ui.user

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.edlo.demogithub.R
import com.edlo.demogithub.databinding.ActivityLoginBinding
import com.edlo.demogithub.ui.BaseActivity
import com.edlo.demogithub.ui.OAuthWebViewFragment

class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        setSupportActionBar(binding.toolbarLayout.toolbar)

        supportActionBar?.let { actionBar ->
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeButtonEnabled(true)
        }

        if (savedInstanceState == null) {
            transFragment(LoginFragment.TAG, false)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> { //back
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun setLoadingViewStatus(isLoading: Boolean) {
        if (isLoading) {
            binding.viewLoading.visibility = View.VISIBLE
        } else {
            binding.viewLoading.visibility = View.GONE
        }
    }

    fun goOAuthWebView(loginName: String) {
        transFragment(OAuthWebViewFragment.TAG, true, loginName)
    }

    private fun transFragment(tag: String, addToBackStack: Boolean = true,  args: Any = Any()) {
        var fragTransaction = supportFragmentManager.beginTransaction()
        var frag: Fragment? = null
        when (tag) {
            LoginFragment.TAG -> {
                frag = LoginFragment.newInstance()
            }
            OAuthWebViewFragment.TAG -> {
                frag = OAuthWebViewFragment.newInstance(args as String)
            }
            else -> { }
        }
        frag?.let {
            fragTransaction.replace(R.id.container, it)
            if (addToBackStack) {
                fragTransaction.addToBackStack(tag)
            }
            fragTransaction.commit()
        }
    }
}
