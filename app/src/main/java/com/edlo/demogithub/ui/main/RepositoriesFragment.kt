package com.edlo.demogithub.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.edlo.demogithub.MainActivity
import com.edlo.demogithub.R
import com.edlo.demogithub.databinding.FragmentListBinding
import com.edlo.demogithub.databinding.FragmentMainBinding
import com.edlo.demogithub.ui.BaseActivity
import com.edlo.demogithub.ui.repository.RepositoryPagerAdapter
import kotlinx.coroutines.launch

class RepositoriesFragment : Fragment() {

    companion object {
        val TAG = RepositoriesFragment::class.java.simpleName
        fun newInstance() = RepositoriesFragment()
    }

    private lateinit var viewModel: GitHubRepositoriesViewModel
    private lateinit var binding: FragmentListBinding
    private var adapter = RepositoriesAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = activity
        binding.listView.adapter = adapter

        viewModel = ViewModelProvider(activity as ViewModelStoreOwner).get(GitHubRepositoriesViewModel::class.java)
        viewModel.getIsLoading().observe(activity as LifecycleOwner, {
            if(activity is BaseActivity) {
                (activity as BaseActivity).setLoadingViewStatus(it)
            }
        })
        viewModel.getQueryUserName().observe(activity as LifecycleOwner, {
            setTitleText(it)
        })
        viewModel.getRepositories().observe(activity as LifecycleOwner, {
            if(it.size == 0) {
                binding.txtListEmpty.visibility = View.VISIBLE
            } else {
                binding.txtListEmpty.visibility = View.GONE
            }
            adapter.data = it
        })

        binding.inputSearch.setOnEditorActionListener { view, actionId, _ ->
            when(actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    if(activity is BaseActivity) {
                        (activity as BaseActivity).hideKeyboard(view.windowToken)
                    }
                    viewModel.setQueryUserName(binding.inputSearch.text.toString())
                    false
                }
                else -> false
            }
        }

        lifecycleScope.launchWhenResumed {
            if(activity is MainActivity) {
                viewModel.getQueryUserName().value?.let {
                    setTitleText(it)
                }
            }
        }
    }

    private fun setTitleText(it: String) {
        if (it.isNotEmpty()) {
            (activity as MainActivity).setActionBarTitle(it)
        } else {
            (activity as MainActivity).setActionBarTitle(getString(R.string.app_name))
        }
    }

}
