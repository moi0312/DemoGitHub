package com.edlo.demogithub.ui.repository

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.edlo.demogithub.R
import com.edlo.demogithub.databinding.FragmentListBinding
import com.edlo.demogithub.databinding.ItemCommitBinding
import com.edlo.demogithub.net.data.Commit
import com.edlo.demogithub.ui.BaseActivity
import com.edlo.demogithub.util.GlideApp
import com.edlo.demogithub.util.Log

class CommitsFragment: Fragment() {
    companion object {
        val TAG = CommitsFragment::class.java.simpleName
        fun newInstance() = CommitsFragment()
    }

    private lateinit var binding: FragmentListBinding
    private lateinit var viewModel: RepositoryViewModel
    private var adapter = CommitsAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        binding.lifecycleOwner = activity
        binding.layoutSearch.visibility = View.GONE
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.listView.adapter = adapter

        if(parentFragment is TabPagerFragment) {
            viewModel = (parentFragment as TabPagerFragment).getViewModel()
        } else {
            viewModel = ViewModelProvider(activity as ViewModelStoreOwner).get(RepositoryViewModel::class.java)
        }
        viewModel.getIsLoading().observe(activity as LifecycleOwner, {
            if(activity is BaseActivity) {
                (activity as BaseActivity).setLoadingViewStatus(it)
            }
        })
        viewModel.getCommits().observe(activity as LifecycleOwner, {
            if(it.size == 0) {
                binding.txtListEmpty.visibility = View.VISIBLE
            } else {
                binding.txtListEmpty.visibility = View.GONE
            }
            adapter.data = it
        })
        viewModel.reqListCommits()
    }
}

class CommitsAdapter : RecyclerView.Adapter<CommitsAdapter.ViewHolder>() {
    companion object {
        var TAG = CommitsAdapter::class.java.simpleName
    }

    var data = ArrayList<Commit>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder.create(parent)
    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(data.get(position))
    }

    class ViewHolder: RecyclerView.ViewHolder {
        companion object {
            fun create(parent: ViewGroup): ViewHolder {
                var binding = ItemCommitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHolder(binding, parent.context)
            }
        }

        private var context: Context
        private val binding: ItemCommitBinding
        private lateinit var item: Commit

        constructor(binding: ItemCommitBinding, context: Context): super(binding.root) {
            this.binding = binding
            this.context = context
        }

//        init {
//            itemView.setOnClickListener {
//            }
//        }

        fun bindTo(item: Commit) {
            this.item = item
            item.commit?.let {
                binding.txtMsg.text = it.message
                binding.txtCommitter.text = "committed by ${it.committer.name}"
                binding.txtDate.text = it.committer.date
            }
            item.committer?.avatarUrl?.let { url ->
                GlideApp.with(context)
                    .load(url)
                    .into(binding.imgUser)
            }
        }

    }
}