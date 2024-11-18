package com.example.hola

import LoginRequest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



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

        val loginbtn : Button = findViewById<Button>(R.id.LoginButton)


        loginbtn.setOnClickListener {
            val email = findViewById<EditText>(R.id.emailEditText).text.toString().trim()
            val password = findViewById<EditText>(R.id.passwordEditText).text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val loginRequest = LoginRequest(email, password)
            val call: Call<LoginResponse> = RetrofitInstance.apiService.login(loginRequest)

            call.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        val loginResponse = response.body()
                        Toast.makeText(this@Login, "Login Successful: ${loginResponse?.access}", Toast.LENGTH_SHORT).show()
                        // Navigate to the next activity or perform other actions

                        val accessToken = loginResponse?.access

                        // Optionally, store the access token
                        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("access_token", accessToken) // Store the access token
                        editor.apply()

                        // Navigate to the homepage after successful login
                        Toast.makeText(this@Login, "Login Successful", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@Login, OTP::class.java)
                        startActivity(intent)

                        // Optionally, finish the login activity so the user can't return to it by pressing back
                        finish()
                    } else {
                        Toast.makeText(this@Login, "Login Failed: ${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@Login, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }



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