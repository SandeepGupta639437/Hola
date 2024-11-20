package com.example.hola

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OTP : AppCompatActivity() {

    private lateinit var apiService: ApiService
    private var email: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_otp)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.OTP_Page)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        apiService=RetrofitInstance.api
        email = intent.getStringExtra("email")

        val otpEditText: EditText = findViewById(R.id.OTPEditText)
        val verifyOTPButton: Button = findViewById(R.id.OTPValidationButton)

        verifyOTPButton.setOnClickListener {
            val otp = otpEditText.text.toString()

            if (otp.isEmpty()) {
                Toast.makeText(this, "Please enter the OTP", Toast.LENGTH_SHORT).show()
            } else {


                val intent = Intent(this@OTP, ResetPasssword::class.java)
                intent.putExtra("email", email)
                intent.putExtra("OTP",otp)
                startActivity(intent)
            }
        }
    }


    }
