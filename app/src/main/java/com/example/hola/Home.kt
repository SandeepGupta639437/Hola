package com.example.api

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.hola.R
import com.example.hola.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class Home : Fragment(){
  override fun onCreateView(
   inflater: LayoutInflater, container: ViewGroup?,
   savedInstanceState: Bundle?
  ): View? {
   val view = inflater.inflate(R.layout.fragment_home, container, false)

   val tabLayout: TabLayout = view.findViewById(R.id.tablayout)
   val viewPager: ViewPager2 = view.findViewById(R.id.ViewPager)

   // Set up the ViewPager2 adapter
   val adapter = ViewPagerAdapter(this)
   viewPager.adapter = adapter

   // Connect TabLayout with ViewPager2
   TabLayoutMediator(tabLayout, viewPager) { tab, position ->
    tab.text = when (position) {
     0 -> "All"
     1 -> "Following"
     else -> "Tab $position"
    }
   }.attach()

   return view
   }



 }
