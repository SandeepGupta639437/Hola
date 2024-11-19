package com.example.hola

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.api.Home
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val homepage= Home()
    private val reelspage=reelspage()
    private val createpage=create_page()
    private val locationpage=locationpage()
    private val profilepage=profilepage()


    private var activeFragment: Fragment = homepage






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main))


        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }



        supportFragmentManager.beginTransaction().apply {
            add(R.id.frame_layout2, homepage, "Home")
            add(R.id.frame_layout2, reelspage, "Reels").hide(reelspage)
            add(R.id.frame_layout2, createpage, "Create").hide(createpage)
            add(R.id.frame_layout2, locationpage, "Location").hide(locationpage)
            add(R.id.frame_layout2, profilepage, "Profile").hide(profilepage)
            commit()
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

    }
    private fun replaceFragment(fragment: Fragment){
//        if(fragment!=null){
//            val transaction=supportFragmentManager.beginTransaction()
//            transaction.replace(R.id.frame_layout2, fragment)
//            transaction.commit()
//        }

        if (activeFragment != fragment) {
            supportFragmentManager.beginTransaction()
                .hide(activeFragment)
                .show(fragment)
                .commit()
            activeFragment = fragment
        }

    }
}