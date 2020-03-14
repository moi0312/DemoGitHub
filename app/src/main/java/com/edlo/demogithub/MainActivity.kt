package com.edlo.demogithub

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.edlo.demogithub.databinding.ActivityMainBinding
import com.edlo.demogithub.ui.BaseActivity
import com.edlo.demogithub.ui.main.RepositoriesFragment
import com.edlo.demogithub.ui.repository.TabPagerFragment
import com.edlo.demogithub.ui.user.LoginActivity
import com.edlo.demogithub.util.Log
import com.example.testcoroutines.net.data.Repository


class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        val toolbar = binding.toolbarLayout.toolbar
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_github)
        toolbar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(Gravity.LEFT)
        }

//        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_github)
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        supportActionBar!!.setHomeButtonEnabled(true)

//        var toggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.drawerOpen, R.string.drawerClose)
//        toggle.setHomeAsUpIndicator(R.drawable.ic_github)
//        binding.drawerLayout.addDrawerListener(toggle)


        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            when(menuItem.itemId) {
                R.id.menuLogin -> {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
                else -> { }
            }
            binding.drawerLayout.closeDrawers()
            true
        }

        if (savedInstanceState == null) {
            transFragment(RepositoriesFragment.TAG, false)
        }
    }

    override fun setLoadingViewStatus(isLoading: Boolean) {
        if (isLoading) {
            binding.viewLoading.visibility = View.VISIBLE
        } else {
            binding.viewLoading.visibility = View.GONE
        }
    }

    fun goRepositoryDetail(repository: Repository) {
        transFragment(TabPagerFragment.TAG, true, repository)
        setActionBarTitle(repository.fullName)
    }

    fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    private fun transFragment(tag: String, addToBackStack: Boolean = true,  args: Any = Any()) {
        var fragTransaction = supportFragmentManager.beginTransaction()
        var frag: Fragment? = null
        when (tag) {
            RepositoriesFragment.TAG -> {
                frag = RepositoriesFragment.newInstance()
            }
            TabPagerFragment.TAG -> {
                frag = TabPagerFragment.newInstance(args as Repository)
            }
            else -> {
            }
        }
        if (frag != null) {
            fragTransaction.replace(R.id.container, frag)
            if (addToBackStack) {
                fragTransaction.addToBackStack(tag)
            }
            fragTransaction.commit()
        }
    }
}
