package com.edlo.demogithub.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import com.edlo.demogithub.R
import com.edlo.demogithub.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    companion object {
        val TAG = LoginFragment::class.java.simpleName
        fun newInstance(): LoginFragment {
            var frag = LoginFragment()
            return frag
        }
    }
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = activity

        binding.inputLoginName.setOnEditorActionListener { view, actionId, event ->
            when(actionId) {
                EditorInfo.IME_ACTION_SEND -> {
                    if(activity is LoginActivity) {
                        val loginActivity = activity as LoginActivity
                        loginActivity.hideKeyboard(view.windowToken)

                        var loginName = binding.inputLoginName.text.toString().trim()
                        if(loginName.isNotEmpty()) {
                            loginActivity.goOAuthWebView(loginName)
                        }
                    }
                    false
                }
                else -> false
            }
        }

    }
}
