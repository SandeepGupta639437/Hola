package com.example.hola

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hola.Accounts_Center

class passwordandActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_passwordand)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.passwordSecure)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val ChangeYourPassword: TextView = findViewById<TextView>(R.id.ChangePasss)
        ChangeYourPassword.setOnClickListener{
            val intent = Intent(this@passwordandActivity, Changepassword::class.java)
            startActivity(intent)
        }

        val SavedDataLogin: TextView = findViewById<TextView>(R.id.LoginSaved)
        SavedDataLogin.setOnClickListener{
            val intent = Intent(this@passwordandActivity, SavedLogin::class.java)
            startActivity(intent)
        }
    }
}