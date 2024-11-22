package com.example.hola

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ExplorePagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val fragmentList = listOf(
        TrendingFragment(),
        CommunityFragment()
    )

    private val fragmentTitles = listOf(
        "Trending",
        "Community"
    )

    override fun getCount(): Int = fragmentList.size

    override fun getItem(position: Int): Fragment = fragmentList[position]

    override fun getPageTitle(position: Int): CharSequence = fragmentTitles[position]
}
