package com.example.hola




import Backend.*
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
import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Intent
import android.content.res.ColorStateList
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.util.Log
import android.view.MotionEvent
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.hola.Login


class signUpPage : AppCompatActivity() {


    //private lateinit var apiService: ApiService
    private lateinit var apiService:ApiService

    private var isPasswordVis = false


//    private lateinit var passwordStrengthBar: ProgressBar
//    private lateinit var passwordStrengthLabel: TextView

    private lateinit var passwordStrengthBar: CardView
   private lateinit var passwordStrengthLabel: TextView
   private lateinit var progressParent: CardView








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

                    isPasswordVis = !isPasswordVis
                    passwordInput.transformationMethod = if (isPasswordVis) {
                        HideReturnsTransformationMethod.getInstance()
                    } else {


                        passwordTransformation()

                    }
                    // Update eye icon
                    passwordInput.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.lock_icon, 0,
                        if (isPasswordVis) R.drawable.open_eye else R.drawable.close_eye, 0
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
           // val signUpRequest = SignUpRequest(fullName, email, password)

            val signUpRequest = SignUpRequest(
                full_name = fullName,
                email = email,
                password = password
            )
            registerUser(signUpRequest)

            // Call the API
            registerUser(signUpRequest)
        }




        //password strength
//        passwordStrengthBar = findViewById <ProgressBar> (R.id.passwordStrengthBar)
//        passwordStrengthLabel = findViewById(R.id.passwordStrengthLabel)

        passwordStrengthBar = findViewById(R.id.progres)
        passwordStrengthLabel = findViewById(R.id.passwordStrengthLabel)
        progressParent=findViewById<CardView>(R.id.progressParent)

        passwordInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updatePasswordStrength(s.toString())
                }
            override fun afterTextChanged(s: Editable?){}


        })






        //sign up to signin
        val signupTextView = findViewById<TextView>(R.id.signinText)

        signupTextView.setOnClickListener {

            signupTextView.setBackgroundColor(Color.parseColor("#6C60A0"))

            val intent = Intent(this@signUpPage, Login::class.java)
            startActivity(intent)

            signupTextView.postDelayed({

                signupTextView.setBackgroundColor(Color.TRANSPARENT)
            }, 200)
        }










    }

   /* private fun registerUser(signUpRequest: SignUpRequest) {
        CoroutineScope(Dispatchers.Main).launch {
            try {

                val response = apiService.registerUser(signUpRequest)

                if (response.isSuccessful) {
                    val signUpResponse = response.body()

                    if (signUpResponse != null) {

                        Toast.makeText(this@signUpPage, signUpResponse.message, Toast.LENGTH_SHORT).show()

                        if (signUpResponse.message.equals("User registered successfully")) {

                            Toast.makeText(
                                this@signUpPage,
                                "user created registered",
                                Toast.LENGTH_SHORT
                            ).show()


                            val intent = Intent(this@signUpPage, Login::class.java)
                            startActivity(intent)



                        }
//                         else if (signUpResponse.message == "username or email already exists") {
//
//
//                            Toast.makeText(this@signUpPage, "username or email already exists", Toast.LENGTH_SHORT).show()
//                        }
                    }
                }


                else {
                    // Handling unsuccessful responses
                    val errorMessage = response.errorBody()?.string()

                    Log.d(errorMessage, "AchalVishnoi")

                    if (errorMessage?.contains("username or email already exists") == true) {
                        Toast.makeText(this@signUpPage, "Username or email already exists.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@signUpPage, "Registration unsuccessful. Please try again.", Toast.LENGTH_SHORT).show()
                    }
                }



            }

            catch (e: Exception) {

                Toast.makeText(this@signUpPage, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }





    }*/




    private fun registerUser(signUpRequest: SignUpRequest) {

        findViewById<TextView>(R.id.signupAlert).visibility= View.GONE


        CoroutineScope(Dispatchers.Main).launch {
            try {


                val response = apiService.registerUser(signUpRequest)

                if (response.isSuccessful) {
                    val signUpResponse = response.body()
                    if (signUpResponse != null && signUpResponse.message == "User registered successfully") {
                        Toast.makeText(this@signUpPage, "Registration successful!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@signUpPage, Login::class.java)
                        intent.putExtra("email", signUpRequest.email) // Pass email
                        intent.putExtra("password", signUpRequest.password) // Pass password
                        startActivity(intent)
                        finish()
                    }
                } else {
                    val errorMessage = response.errorBody()?.string()
                    if (errorMessage?.contains("User with this email is already registered") == true) {

                        findViewById<TextView>(R.id.signupAlert).visibility= View.VISIBLE

//                        Toast.makeText(this@signUpPage, "Email is already registered.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@signUpPage, "Registration failed. Try again.", Toast.LENGTH_SHORT).show()
                    }
                }

//                if (response.isSuccessful) {
//                    Log.d("SignUpResponse", "Success: ${response.body()}")
//
//                    val intent = Intent(this@signUpPage, Login::class.java)
//                    intent.putExtra("email", signUpRequest.email) // Pass email
//                    intent.putExtra("password", signUpRequest.password) // Pass password
//                    startActivity(intent)
//                    finish()
//
//
//                } else {
//                    Log.d("SignUpResponse", "Error: ${response.errorBody()?.string()}")
//                }



            } catch (e: Exception) {
                Toast.makeText(this@signUpPage, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }



    private fun updatePasswordStrength(password: String) {
        passwordStrengthBar.visibility = View.VISIBLE
        passwordStrengthLabel.visibility = View.VISIBLE
        progressParent.visibility = View.VISIBLE





//        val passwordInput = findViewById<EditText>(R.id.passwordInput)
//
//        if (passwordInput.text.length == 0||passwordStrengthLabel.text.equals("Your Password is strong")) {
//            passwordStrengthBar.visibility = View.GONE
//            passwordStrengthLabel.visibility = View.GONE
//            progressParent.visibility = View.GONE
//
//        }


        val (targetProgress, targetColor, labelText) = when (calculatePasswordStrength(password)) {
            in 0..32 -> Triple(0.25f, Color.parseColor("#E33629"), "Your Password is too weak")
            in 33..99 -> Triple(
                0.50f,
                Color.parseColor("#F8BD00"),
                "Your Password could be stronger"
            )

            else -> Triple(1.0f, Color.parseColor("#319F43"), "Your Password is strong")
        }

        val parentWidth = progressParent.width
        val targetWidth = (parentWidth * targetProgress).toInt()





        val widthAnimator =
            ValueAnimator.ofInt(passwordStrengthBar.layoutParams.width, targetWidth).apply {
                duration = 1000000
                interpolator = android.view.animation.DecelerateInterpolator()
                addUpdateListener { animation ->
                    val animatedWidth = animation.animatedValue as Int
                    passwordStrengthBar.layoutParams = passwordStrengthBar.layoutParams.apply {
                        width = animatedWidth
                    }
                    passwordStrengthBar.requestLayout()

                }
            }


        val currentColor = (passwordStrengthBar.backgroundTintList?.defaultColor ?: Color.GRAY)
        val colorAnimator =
            ValueAnimator.ofObject(ArgbEvaluator(), currentColor, targetColor).apply {
                duration = 1000000
                addUpdateListener { animator ->
                    val animatedColor = animator.animatedValue as Int
               passwordStrengthLabel.textColors
                    passwordStrengthBar.backgroundTintList = ColorStateList.valueOf(animatedColor)
                    passwordStrengthLabel.setTextColor(animatedColor)

                }
            }






        AnimatorSet().apply {
            playTogether(widthAnimator, colorAnimator)
            start()
        }


        passwordStrengthLabel.text = labelText


        if (targetProgress == 1.0f) {
            passwordStrengthLabel.postDelayed({
                passwordStrengthBar.visibility = View.GONE
                passwordStrengthLabel.visibility = View.GONE
                progressParent.visibility = View.GONE
            }, 400)

        }


    }

    private   fun calculatePasswordStrength(password: String): Int {
        var score = 0

        if (password.length >= 8) score += 25
        if (password.matches(".*[A-Z].*".toRegex())) score += 25
        if (password.matches(".*[0-9].*".toRegex())) score += 25
        if (password.matches(".*[@#\$%^&+=].*".toRegex())) score += 25

        return score
    }




}