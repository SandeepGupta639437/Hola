package com.example.hola

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.signIn)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val forgotPass: TextView = findViewById<TextView>(R.id.forgot_pass)
        forgotPass.setOnClickListener{
            val intent = Intent(this@Login, forgotPassword::class.java)
            startActivity(intent)
            finish()
        }

        val pass_display : TextView = findViewById<TextView>(R.id.incorrectpass)
        pass_display.visibility = View.INVISIBLE

        val passwordEditText: EditText = findViewById(R.id.passwordEditText)
        passwordEditText.transformationMethod = passwordTransformation()

    }
}

class passwordTransformation : PasswordTransformationMethod() {
    override fun getTransformation(source: CharSequence, view: View): CharSequence {
        return AsteriskCharSequence(source)
    }

    private class AsteriskCharSequence(private val source: CharSequence) : CharSequence {
        override val length: Int
            get() = source.length

        override fun get(index: Int): Char {

            return '*'
        }

        override fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
            return AsteriskCharSequence(source.subSequence(startIndex, endIndex))
            }
        }
}