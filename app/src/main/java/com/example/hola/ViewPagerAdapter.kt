package com.example.hola

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.api.Home

class ViewPagerAdapter(fa: Home):FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 2;
    }
    override fun createFragment(position: Int): Fragment {
       return when(position){
           0->HomeAll()
           1->HomeFollowing()
           else->HomeAll()
       }
    }
}