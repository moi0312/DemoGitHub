package com.edlo.demogithub.ui.repository

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.edlo.demogithub.R
import com.edlo.demogithub.util.Log

class RepositoryPagerAdapter : FragmentPagerAdapter {

    private var context: Context
    private var fragmentManager: FragmentManager
    private var tabTitles: Array<String>

    companion object {
        private val TAG = RepositoryPagerAdapter::class.java.simpleName
    }

    constructor(context: Context, fm: FragmentManager) : super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        this.fragmentManager = fm
        this.context = context
        this.tabTitles = context.resources.getStringArray(R.array.tabTitles)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return this.tabTitles[position]
    }

    override fun getItem(position: Int): Fragment {
        var frag: Fragment?
        Log.v(TAG, "onTabSelect: $position" )
        when(position) {
            0 -> {
                frag = fragmentManager.findFragmentByTag(CommitsFragment.TAG)
                if(frag == null) {
                    frag = CommitsFragment.newInstance()
                }
                return frag
            }
            else -> {
                frag = fragmentManager.findFragmentByTag(CollaboratorsFragment.TAG)
                if(frag == null) {
                    frag = CollaboratorsFragment.newInstance()
                }
                return frag
            }
        }
    }

    override fun getCount(): Int {
        return tabTitles.size
    }
}
