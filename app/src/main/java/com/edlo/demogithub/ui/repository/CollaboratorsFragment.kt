package com.edlo.demogithub.ui.repository

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
import com.edlo.demogithub.R
import com.edlo.demogithub.databinding.FragmentListBinding
import com.edlo.demogithub.databinding.ItemCollaboratorBinding
import com.edlo.demogithub.net.data.User
import com.edlo.demogithub.ui.BaseActivity
import com.edlo.demogithub.util.GlideApp

class CollaboratorsFragment: Fragment() {
    companion object {
        val TAG = CollaboratorsFragment::class.java.simpleName
        fun newInstance() = CollaboratorsFragment()
    }

    private lateinit var binding: FragmentListBinding
    private lateinit var viewModel: RepositoryViewModel
    private var adapter = CollaboratorsAdapter()

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

        viewModel.getCollaborators().observe(activity as LifecycleOwner, {
            if(it.size == 0) {
                binding.txtListEmpty.visibility = View.VISIBLE
            } else {
                binding.txtListEmpty.visibility = View.GONE
            }
            adapter.data = it
        })
        viewModel.reqListCollaborators()
    }
}

class CollaboratorsAdapter : RecyclerView.Adapter<CollaboratorsAdapter.ViewHolder>() {
    companion object {
        var TAG = CollaboratorsAdapter::class.java.simpleName
    }

    var data = ArrayList<User>()
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
                var binding = ItemCollaboratorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHolder(binding)
            }
        }

        private val binding: ItemCollaboratorBinding
        private lateinit var item: User

        constructor(binding: ItemCollaboratorBinding): super(binding.root) {
            this.binding = binding
        }

//        init {
//            itemView.setOnClickListener {
//            }
//        }

        fun bindTo(item: User) {
            this.item = item
            binding.txtUserName.text = item.loginName
            item.avatarUrl.let {
                GlideApp.with(binding.root.context)
                    .load(it)
                    .skipMemoryCache(false)
                    .into(binding.imgUser)
            }
        }

    }
}
