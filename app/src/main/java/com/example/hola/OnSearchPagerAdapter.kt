package com.example.hola

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnSearchPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private val fragmentList = listOf(
        top(),
        Accounts(),
        TagsFragment(),
        CommunityFragment()
    )

    private val fragmentTitles = listOf(
        "Top",
        "Accounts",
        "Tags",
        "Community"
    )

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]

    fun getPageTitle(position: Int): String = fragmentTitles[position]
}
