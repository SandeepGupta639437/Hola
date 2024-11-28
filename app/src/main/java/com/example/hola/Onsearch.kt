package com.example.hola

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class Onsearch : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onsearch)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize TabLayout and ViewPager2
        val onsearchTabLayout: TabLayout = findViewById(R.id.OnSearchtabLayout)
        val onsearchViewPager: ViewPager2 = findViewById(R.id.OnSearchViewPager)

        // Create an instance of your Adapter
        val adapter = OnSearchPagerAdapter(this) // Use 'this' for activity context
        onsearchViewPager.adapter = adapter

        // Set up TabLayout with ViewPager2 using TabLayoutMediator
        TabLayoutMediator(onsearchTabLayout, onsearchViewPager) { tab, position ->
            tab.text = adapter.getPageTitle(position) // Set tab titles from the adapter
        }.attach()
    }
}
