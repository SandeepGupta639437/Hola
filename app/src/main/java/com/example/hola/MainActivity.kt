package com.example.hola

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val homepage=Home()
    private val reelspage=reelspage()
    private val createpage=create_page()
    private val locationpage=locationpage()
    private val profilepage=profilepage()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.homeic->replaceFragment(homepage)
                R.id.relsics->replaceFragment(reelspage)
                R.id.createics->replaceFragment(createpage)
                R.id.locationsic->replaceFragment(locationpage)
                R.id.profileic->replaceFragment(profilepage)
            }
            true
        }
        replaceFragment(homepage)
    }
    private fun replaceFragment(fragment: Fragment){
        if(fragment!=null){
            val transaction=supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout2, fragment)
            transaction.commit()
        }
    }
}