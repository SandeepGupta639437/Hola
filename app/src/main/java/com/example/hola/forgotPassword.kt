package com.example.hola

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import Backend.*
import android.annotation.SuppressLint
import android.content.Intent
import android.telecom.Call
import android.view.View
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class forgotPassword : AppCompatActivity() {

    private lateinit var apiService: ApiService


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forgot_password)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Forgot_Password)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        findViewById<TextView>(R.id.doseNotExistAlert).visibility= View.GONE

        val getOTPButton: Button = findViewById(R.id.GetOTPButton)
        val emailEditText: TextInputEditText = findViewById(R.id.forgotEmailEditText)

        getOTPButton.setOnClickListener {
            val email = emailEditText.text.toString()

            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter your email address", Toast.LENGTH_SHORT).show()
            } else {
                // Call API to request OTP
                apiService=RetrofitInstance.api
                requestOTP(email)
            }
        }


    }




    private fun requestOTP(email: String) {
        // Show loading message or progress (optional)
        // Toast.makeText(this, "Requesting OTP...", Toast.LENGTH_SHORT).show()

        CoroutineScope(Dispatchers.Main).launch {
            try {
                // Create ForgotPasswordRequest with the email entered by the user
                val forgotPasswordRequest = ForgotPasswordRequest(email)

                // Make the API call
                val response: Response<ForgotPasswordResponse> = apiService.forgotPassword(forgotPasswordRequest)

                if (response.isSuccessful) {
                    // Success response
                    val forgotPasswordResponse = response.body()
                    if (forgotPasswordResponse?.message == "OTP sent to email.") {
                        Toast.makeText(this@forgotPassword, "OTP sent to email.", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@forgotPassword, OTP::class.java)
                        intent.putExtra("email", email)
                        startActivity(intent)
                        finish()
                    }
                } else {

                    val errorMessage = response.errorBody()?.string()
                    if (errorMessage?.contains("User with this email does not exist") == true) {
                        findViewById<TextView>(R.id.doseNotExistAlert).visibility= View.VISIBLE
                        Toast.makeText(this@forgotPassword, "No user found with this email.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@forgotPassword, "Request failed. Please try again.", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this@forgotPassword, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }





}