package com.example.hola

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Splashscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splashscreen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.splashScreen)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val logo = findViewById<ImageView>(R.id.logo)

        // Load animations
        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fadein)
        val scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scaleup)
        val rotateLeftAnimation = AnimationUtils.loadAnimation(this, R.anim.rotationleft)


        // Start animations
        logo.startAnimation(fadeInAnimation)
        logo.startAnimation(scaleAnimation)


        // After the scale-up is done, start the rotation animations
        scaleAnimation.setAnimationListener(object : android.view.animation.Animation.AnimationListener {
            override fun onAnimationStart(animation: android.view.animation.Animation?) {}
            override fun onAnimationRepeat(animation: android.view.animation.Animation?) {}

            override fun onAnimationEnd(animation: android.view.animation.Animation?) {
                logo.startAnimation(rotateLeftAnimation)  // Rotate left (counterclockwise)
                rotateLeftAnimation.setAnimationListener(object : android.view.animation.Animation.AnimationListener {
                    override fun onAnimationStart(animation: android.view.animation.Animation?) {}
                    override fun onAnimationRepeat(animation: android.view.animation.Animation?) {}

                    override fun onAnimationEnd(animation: android.view.animation.Animation?) {
//                        logo.startAnimation(rotateRightAnimation)  // Rotate right (clockwise)
                    }
                })
            }
        })

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }, 3000) // 3 seconds delay
    }
}