package com.example.hola



import Backend.ApiService
import Backend.RetrofitInstance
import Backend.SignUpRequest
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import passwordTransformation
import Backend.*
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import androidx.core.content.ContextCompat


class signUpPage : AppCompatActivity() {


    private lateinit var apiService: ApiService

    private var isPasswordVisible = false





    @SuppressLint("MissingInflatedId", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val passwordInput = findViewById<EditText>(R.id.passwordInput)


        val tvAgreement = findViewById<TextView>(R.id.tv_agreement)

        val agreementText = "By signing up, you agree to our Terms & Conditions and Privacy Policy"
        val spannableString = SpannableString(agreementText)
        val purpleColor = Color.parseColor("#BBACF2")
        val termsClickable = object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(this@signUpPage, "Terms & Conditions clicked", Toast.LENGTH_SHORT).show()

            }

            override fun updateDrawState(ds: android.text.TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
                ds.color = purpleColor
            }
        }
        val termsStartIndex = agreementText.indexOf("Terms & Conditions")
        val termsEndIndex = termsStartIndex + "Terms & Conditions".length
        spannableString.setSpan(termsClickable, termsStartIndex, termsEndIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(ForegroundColorSpan(purpleColor), termsStartIndex, termsEndIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val privacyClickable = object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(this@signUpPage, "Privacy Policy clicked", Toast.LENGTH_SHORT).show()
             }
            override fun updateDrawState(ds: android.text.TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
                ds.color = purpleColor
            }
        }
        val privacyStartIndex = agreementText.indexOf("Privacy Policy")
        val privacyEndIndex = privacyStartIndex + "Privacy Policy".length
        spannableString.setSpan(privacyClickable, privacyStartIndex, privacyEndIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(ForegroundColorSpan(purpleColor), privacyStartIndex, privacyEndIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        tvAgreement.text = spannableString
        tvAgreement.movementMethod = LinkMovementMethod.getInstance()







       passwordInput.transformationMethod = passwordTransformation()



        //password show effect

        val eyeOpenDrawable = ContextCompat.getDrawable(this, R.drawable.open_eye)
        val eyeCloseDrawable = ContextCompat.getDrawable(this, R.drawable.close_eye)

        // TextWatcher to show/hide
        passwordInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {

                    passwordInput.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.lock_icon, 0, 0, 0
                    )
                } else {
                    // Show the eye icon
                    passwordInput.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.lock_icon, 0, R.drawable.close_eye, 0
                    )
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
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
                        R.drawable.lock_icon, 0,
                        if (isPasswordVisible) R.drawable.open_eye else R.drawable.close_eye, 0
                    )
                    // Move cursor to the end
                    passwordInput.setSelection(passwordInput.text.length)
                    true
                }


                else {
                    false

                }
            }


            else {
                false
            }
        }






        apiService = RetrofitInstance.api

        // Assuming you have EditText fields for full name, email, and password
        val nameInput = findViewById<EditText>(R.id.nameInput)
        val emailInput = findViewById<EditText>(R.id.emailInput)


        val signUpButton = findViewById<Button>(R.id.CreatAccountButton)
        signUpButton.setOnClickListener {
            val fullName = nameInput.text.toString()
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            // Create the sign-up request object
            val signUpRequest = SignUpRequest(fullName, email, password)

            // Call the API
            registerUser(signUpRequest)
        }
    }

    private fun registerUser(signUpRequest: SignUpRequest) {
        CoroutineScope(Dispatchers.Main).launch {
            try {

                val response = apiService.registerUser(signUpRequest)

                if (response.isSuccessful) {
                    val signUpResponse = response.body()

                    if (signUpResponse != null) {

                        Toast.makeText(this@signUpPage, signUpResponse.message, Toast.LENGTH_SHORT).show()

                        if (signUpResponse.message == "user created registered") {

                            Toast.makeText(this@signUpPage, "user created registered", Toast.LENGTH_SHORT).show()


                        } else if (signUpResponse.message == "username or email already exists") {
                            // Handle the case where the username or email is already taken
                            // Show an error message to the user

                            Toast.makeText(this@signUpPage, "username or email already exists", Toast.LENGTH_SHORT).show()
                        }
                    }
                }


                else {

                    Toast.makeText(this@signUpPage, "Registration failed", Toast.LENGTH_SHORT).show()
                }



            }

            catch (e: Exception) {

                Toast.makeText(this@signUpPage, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }





    }




}