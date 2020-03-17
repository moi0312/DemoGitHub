package com.edlo.demogithub.ui

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.edlo.demogithub.BuildConfig
import com.edlo.demogithub.DemoGitHubApplication
import com.edlo.demogithub.R
import com.edlo.demogithub.databinding.FragmentWebViewBinding
import com.edlo.demogithub.util.Log
import com.edlo.demogithub.util.SharedPreferencesHelper
import com.example.testcoroutines.net.ApiGitHub
import com.example.testcoroutines.net.ApiGutHubHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class OAuthWebViewFragment : Fragment() {
    companion object {
        val TAG =  OAuthWebViewFragment::class.java.simpleName
        private const val ARG_STRING_LOGIN_NAME =  "loginName"

        fun newInstance(loginName: String): OAuthWebViewFragment {
            var frag = OAuthWebViewFragment()
            var args = Bundle()
            args.putString(ARG_STRING_LOGIN_NAME, loginName)
            frag.arguments = args
            return frag
        }
    }

    private lateinit var binding: FragmentWebViewBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_web_view, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = activity
        val webClient = GitHubLoginWebViewClient(activity!!)
        binding.webView.webViewClient = webClient

        arguments?.getString(ARG_STRING_LOGIN_NAME)?.let { loginName ->
            webClient.currentLoginUser = loginName
            var scope = "user" //"user", "repo"
            var url = "${ApiGitHub.POST_OAUTH_AUTHORIZE}?client_id=${BuildConfig.GIT_HUB_CLIENT_ID}&scope=${scope}&login=${loginName}"
            binding.webView.loadUrl(url)
        }
    }
}

class GitHubLoginWebViewClient(val activity: FragmentActivity): WebViewClient() {
    companion object {
        val TAG =  GitHubLoginWebViewClient::class.java.simpleName
    }

    var currentLoginUser: String? = null

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        request?.let { req ->
            val code = req.url.getQueryParameter("code")
            code?.let {
                Log.d(TAG, "shouldOverrideUrlLoading: code: $it")
                GlobalScope.launch(Dispatchers.Main) {
                    var accessToken = ApiGutHubHelper.INSTANCE.exchangeAccessTokenByCode(code)
                    accessToken?.let { token ->
                        Log.d(TAG, "shouldOverrideUrlLoading: token: $token")
                        currentLoginUser?.let { user ->
                            SharedPreferencesHelper.HELPER.putToken(user, token)
                        }
                    }
                }
                activity.finish()
                return true
            }
        }
        return super.shouldOverrideUrlLoading(view, request)
    }



    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        if(activity is BaseActivity) {
            (activity as BaseActivity).setLoadingViewStatus(true)
        }
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        if(activity is BaseActivity) {
            (activity as BaseActivity).setLoadingViewStatus(false)
        }
    }
}
