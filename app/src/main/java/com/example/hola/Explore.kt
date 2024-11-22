package com.example.hola

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class Explore : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_explore)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tabLayout: TabLayout =  findViewById<TabLayout>(R.id.ExploretabLayout)
        val viewPager: ViewPager = findViewById<ViewPager>(R.id.ExploreViewPager)
        val searchPageIcon : ImageView = findViewById<ImageView>(R.id.Searchicon)

        searchPageIcon.setOnClickListener{
            val intent = Intent(this@Explore , Onsearch::class.java)
            startActivity()
        }

        val adapter = ExplorePagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter

        tabLayout.setupWithViewPager(viewPager)


    }
}