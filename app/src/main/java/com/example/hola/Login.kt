package com.example.hola


import Backend.LoginRequest
import Backend.LoginResponse

import com.example.hola.ApiService


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.api.Home
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import passwordTransformation
import retrofit2.Call
import retrofit2.Callback




class Login : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    private var isPasswordVisible = false

    private lateinit var apiService: ApiService


    @SuppressLint("ClickableViewAccessibility")
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

        val pass_display: TextView = findViewById<TextView>(R.id.incorrectpass)
        pass_display.visibility = View.INVISIBLE

        val passwordEditText: EditText = findViewById(R.id.passwordEditText)
        passwordEditText.transformationMethod = passwordTransformation()

        val loginbtn: Button = findViewById<Button>(R.id.LoginButton)


        //for intent to forgot password

        forgotPass.setOnClickListener {

            forgotPass.setBackgroundColor(Color.parseColor("#ddccef"))

            val intent = Intent(this@Login, forgotPassword::class.java)
            startActivity(intent)


            forgotPass.postDelayed({

                forgotPass.setBackgroundColor(Color.TRANSPARENT)
            }, 200)
        }


        loginbtn.setOnClickListener {
            val email = findViewById<EditText>(R.id.emailEditText).text.toString().trim()
            val password = findViewById<EditText>(R.id.passwordEditText).text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            apiService = RetrofitInstance.api

            val loginRequest = LoginRequest(email, password)




            val emailFromSignup = intent.getStringExtra("email") // Retrieve email
            val passwordFromSignup = intent.getStringExtra("password") // Retrieve password

            if (email != null) {
                findViewById<EditText>(R.id.emailEditText).setText(email)
            }
            if (password != null) {
                passwordEditText.setText(password)
            }






            CoroutineScope(Dispatchers.Main).launch {
                try {
                    // Call the login API
                    val response = apiService.login(loginRequest)

                    if (response.isSuccessful) {
                        // Parse the login response
                        val loginResponse = response.body()
                        val accessToken = loginResponse?.access ?: ""

                        if (accessToken.isNotEmpty()) {
                            // Save the token in SharedPreferences
                            val sharedPreferences =
                                getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                            sharedPreferences.edit().apply {
                                putString("access_token", accessToken)
                                apply()
                            }

                            // Display success and navigate to Home
                            Toast.makeText(this@Login, "Login Successful!", Toast.LENGTH_SHORT)
                                .show()
                            startActivity(Intent(this@Login, MainActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(
                                this@Login,
                                "Login failed. Empty token received.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        // Parse error response if available
                        val errorBody = response.errorBody()?.string()
                        Log.d("LoginError", "Error Body: $errorBody")

                        if (errorBody?.contains("Invalid username or password") == true) {

                            pass_display.visibility = View.VISIBLE
                            Toast.makeText(
                                this@Login,
                                "Invalid username or password.",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (errorBody?.contains("No active account found with the given credentials") == true) {
                            Toast.makeText(
                                this@Login,
                                "No active account found with the given credentials",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                this@Login,
                                "Login failed. Try again.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        pass_display.visibility = View.VISIBLE
                    }
                } catch (e: Exception) {
                    // Handle API call failure
                    Toast.makeText(this@Login, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    Log.e("LoginException", e.message ?: "Unknown error")
                }
            }

        }


            val signupTextView = findViewById<TextView>(R.id.signupText)

            signupTextView.setOnClickListener {

                signupTextView.setBackgroundColor(Color.parseColor("#6C60A0"))

                val intent = Intent(this@Login, signUpPage::class.java)
                startActivity(intent)

                signupTextView.postDelayed({

                    signupTextView.setBackgroundColor(Color.TRANSPARENT)
                }, 200)
            }


            //for password transformation and toggle effect


            val passwordInput = findViewById<EditText>(R.id.passwordEditText)


            passwordInput.transformationMethod = passwordTransformation()


            //password show effect

            val eyeOpenDrawable = ContextCompat.getDrawable(this, R.drawable.open_eye)
            val eyeCloseDrawable = ContextCompat.getDrawable(this, R.drawable.close_eye)

            // TextWatcher to show/hide
            passwordInput.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (s.isNullOrEmpty()) {

                        passwordInput.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.lock, 0, 0, 0
                        )
                    } else {
                        // Show the eye icon
                        passwordInput.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.lock, 0, R.drawable.close_eye, 0
                        )
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })

            // Set onTouchListener
            passwordInput.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    val drawableEnd = passwordInput.compoundDrawables[2]
                    if (drawableEnd != null && event.rawX >= (passwordInput.right - drawableEnd.bounds.width())) {

                        isPasswordVisible = !isPasswordVisible
                        passwordInput.transformationMethod = if (isPasswordVisible) {
                            HideReturnsTransformationMethod.getInstance()
                        } else {


                            passwordTransformation()

                        }
                        // Update eye icon
                        passwordInput.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.lock, 0,
                            if (isPasswordVisible) R.drawable.open_eye else R.drawable.close_eye, 0
                        )
                        // Move cursor to the end
                        passwordInput.setSelection(passwordInput.text.length)
                        true
                    } else {
                        false

                    }
                } else {
                    false
                }
            }





    }


}





