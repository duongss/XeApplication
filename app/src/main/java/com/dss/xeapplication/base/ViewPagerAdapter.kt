package com.dss.xeapplication.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
    manager: FragmentManager,
    lifecycle: Lifecycle,
    val fragments: ArrayList<BaseFragment<*>>
) :
    FragmentStateAdapter(manager, lifecycle) {

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}