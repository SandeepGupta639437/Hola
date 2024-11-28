package com.example.hola

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hola.Settings

class Accounts_Center : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_accounts_center)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.account_center_page)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val passSecure: TextView = findViewById<TextView>(R.id.passwordandSecure)
        passSecure.setOnClickListener{
            val intent = Intent(this@Accounts_Center, passwordandActivity::class.java)
            startActivity(intent)
            finish()
        }

        val personal: TextView = findViewById<TextView>(R.id.personalText)
        personal.setOnClickListener{
            val intent = Intent(this@Accounts_Center, PersonalDetails::class.java)
            startActivity(intent)
            finish()
        }
    }
}