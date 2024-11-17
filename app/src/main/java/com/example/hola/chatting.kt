package com.example.hola

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class chatting : AppCompatActivity() {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chatting)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val rootView = findViewById<View>(R.id.main)
        rootView.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            rootView.getWindowVisibleDisplayFrame(rect)
            val screenHeight = rootView.rootView.height
            val keyboardHeight = screenHeight - rect.bottom
            if (screenHeight * 0.15 < keyboardHeight) {
                // Keyboard is visible, adjust your layou

                val layout = findViewById<LinearLayout>(R.id.main1)
                val params = layout.layoutParams as ViewGroup.LayoutParams

                // Adjust height to account for the keyboard
                params.height = screenHeight - keyboardHeight
                layout.layoutParams = params


            } else {
                // Keyboard is hidden, reset the layout'

                val layout = findViewById<LinearLayout>(R.id.main1)
                val params = layout.layoutParams as ViewGroup.LayoutParams

                // Reset height to original (you may store this beforehand if needed)
                params.height = ViewGroup.LayoutParams.MATCH_PARENT
                layout.layoutParams = params
            }
        }

    }
}