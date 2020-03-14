package com.edlo.demogithub.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edlo.demogithub.MainActivity
import com.edlo.demogithub.databinding.ItemRepositoryBinding
import com.edlo.demogithub.ui.BaseActivity
import com.edlo.demogithub.util.Log
import com.example.testcoroutines.net.data.Repository

class RepositoriesAdapter: RecyclerView.Adapter<RepositoriesAdapter.ViewHolder>() {
    companion object {
        var TAG = RepositoriesAdapter::class.java.simpleName
    }

    var data = ArrayList<Repository>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.create(parent)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(data.get(position))
    }

    class ViewHolder: RecyclerView.ViewHolder {
        companion object {
            fun create(parent: ViewGroup): ViewHolder {
                var binding = ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHolder(binding)
            }
        }

        private val binding: ItemRepositoryBinding
        private lateinit var item: Repository

        constructor(binding: ItemRepositoryBinding): super(binding.root) {
            this.binding = binding
        }

        init{
            itemView.setOnClickListener {
                if(it.context is MainActivity) {
                    (it.context as MainActivity).goRepositoryDetail(item)
                } else {
                    Log.d("it.context !is BaseActivity")
                }
            }
        }

        fun bindTo(item: Repository) {
            this.item = item
            binding.txtName.text = item.name
            binding.txtDesc.text = item.description
            binding.txtLaguage.text = item.language
            binding.txtLastUpdate.text = item.updatedAt
        }

    }
}