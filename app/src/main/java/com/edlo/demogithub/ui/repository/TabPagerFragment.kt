package com.edlo.demogithub.ui.repository

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenResumed
import com.edlo.demogithub.MainActivity
import com.edlo.demogithub.R
import com.edlo.demogithub.databinding.FragmentTabPagerBinding
import com.example.testcoroutines.net.data.Repository
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class TabPagerFragment : Fragment() {

    companion object {
        val TAG = TabPagerFragment::class.java.simpleName
        private const val ARG_REPO = "repository"

        fun newInstance(repository: Repository): TabPagerFragment {
            var frag = TabPagerFragment()
            var args = Bundle()
            args.putSerializable(ARG_REPO, repository)
            frag.arguments = args
            return frag
        }
    }

    private lateinit var binding: FragmentTabPagerBinding
    private lateinit var viewModel: RepositoryViewModel
    private lateinit var pagerAdapter: RepositoryPagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_pager, container, false)
        binding.lifecycleOwner = activity

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(activity as ViewModelStoreOwner).get(RepositoryViewModel::class.java)
        var repo = arguments!!.getSerializable(ARG_REPO)
        viewModel.setRepo(repo as Repository)

        pagerAdapter = RepositoryPagerAdapter(activity as Context, childFragmentManager)
        binding.viewPager.adapter = pagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)

//        if(activity is MainActivity) {
//            (activity as MainActivity).setToolbarTitle(viewModel.getRepo().value?.name)
//        }
    }

    fun getViewModel(): RepositoryViewModel  = viewModel

}
