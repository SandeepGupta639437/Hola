package com.example.hola

import Backend.ResetPasswordRequest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.graphics.Color

import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ResetPasssword : AppCompatActivity() {

    private lateinit var apiService: ApiService
    private var email: String? = null
    private var otp: String? = null


    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reset_passsword)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Reset_Password)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Retrieve email and OTP passed from the OTP activity
         email = intent.getStringExtra("email")
         otp = intent.getStringExtra("OTP")

        // UI Components
        val newPasswordEditText: EditText = findViewById(R.id.NewPassEditText)
        val confirmPasswordEditText: EditText = findViewById(R.id.ConfirmPassEditText)
        val newPasswordLayout: TextInputLayout = findViewById(R.id.NewPassLayout)
        val confirmPasswordLayout: TextInputLayout = findViewById(R.id.ConfirmPassLayout)
        val resetPasswordButton: Button = findViewById(R.id.ResetPasswordButton)

        resetPasswordButton.setOnClickListener {
            val newPassword = newPasswordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()
            apiService=RetrofitInstance.api

            // Validate inputs
            when {
                newPassword.isEmpty() -> {
                    showError(newPasswordLayout, "New password cannot be empty")
                }
                confirmPassword.isEmpty() -> {
                    showError(confirmPasswordLayout, "Confirm password cannot be empty")
                }
                newPassword != confirmPassword -> {
                    showError(confirmPasswordLayout, "Passwords do not match")
                }
                newPassword.length < 6 -> {
                    showError(newPasswordLayout, "Password must be at least 6 characters long")
                }
                else -> {


                    // Inputs are valid, reset the error states
                    clearError(newPasswordLayout)
                    clearError(confirmPasswordLayout)
                    // Proceed with the API call
                    resetPassword(newPassword)
                }
            }
        }
    }

    private fun resetPassword(newPassword: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Create the request body
                val resetPasswordRequest = ResetPasswordRequest(
                    email = email ?: "",
                    otp = otp ?: "",
                    new_password = newPassword)

                // Make the API call
                val response = apiService.resetPassword(resetPasswordRequest)

                // Switch to the main thread for UI updates
                CoroutineScope(Dispatchers.Main).launch {
                    if (response.isSuccessful) {
                        val message = response.body()?.message
                        if (message == "Password successfully reset.") {
                            Toast.makeText(
                                this@ResetPasssword,
                                "Password reset successfully!",
                                Toast.LENGTH_SHORT
                            ).show()

                            val intent= Intent(this@ResetPasssword, Login::class.java)
                            startActivity(intent)


                            finish() // Close the activity or navigate to the login screen
                        } else {
                            Toast.makeText(this@ResetPasssword, "Error: $message", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        val error = response.errorBody()?.string()
                        handleErrorResponse(error)
                    }
                }
            } catch (e: Exception) {
                // Handle exceptions
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(this@ResetPasssword, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun handleErrorResponse(error: String?) {
        when {
            error?.contains("otp has expired") == true -> {
                Toast.makeText(this, "OTP has expired. Please request a new one.", Toast.LENGTH_SHORT).show()
            }
            error?.contains("Invalid OTP.") == true -> {
                Toast.makeText(this, "Invalid OTP. Please try again.", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, "Failed to reset password. Please try again.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Utility function to show an error in a TextInputLayout
    private fun showError(layout: TextInputLayout, errorMessage: String) {
        layout.error = errorMessage
        layout.boxStrokeColor = Color.RED
    }

    // Utility function to clear the error from a TextInputLayout
    private fun clearError(layout: TextInputLayout) {
        layout.error = null
        layout.boxStrokeColor = Color.TRANSPARENT

    }
}