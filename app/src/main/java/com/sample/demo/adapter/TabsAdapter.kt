package com.sample.demo.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sample.demo.fragment.MetadataFragment
import com.sample.demo.fragment.user.UsersFragment

internal class TabsAdapter(private val usersFragment: UsersFragment, private val metadataFragment: MetadataFragment, fm: FragmentManager, private var tabsCount: Int) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                usersFragment
            }
            1 -> {
                metadataFragment
            }
            else -> getItem(position)
        }
    }

    override fun getCount(): Int {
        return tabsCount
    }
}