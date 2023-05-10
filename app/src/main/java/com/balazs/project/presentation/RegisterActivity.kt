package com.balazs.project.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.balazs.project.R
import com.balazs.project.utils.TransparentStatusBarHandler
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    lateinit var txt_name: EditText
    lateinit var txt_email: EditText
    lateinit var txt_password: EditText
    lateinit var txt_confirmPassword: EditText
    lateinit var txt_SignIn : TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: RegisterVM


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        TransparentStatusBarHandler.initTransparentStatusBar(window)

        viewModel = ViewModelProvider(this).get(RegisterVM::class.java)
        auth = FirebaseAuth.getInstance()
        txt_name = findViewById(R.id.et_name)
        txt_email = findViewById(R.id.et_email)
        txt_password = findViewById(R.id.et_pass_enter)
        txt_confirmPassword = findViewById(R.id.et_pass_renter)
        txt_SignIn = findViewById(R.id.txt_signin)
        var button_register:Button = findViewById(R.id.button_register)

        txt_password.addTextChangedListener {
            if (txt_password.text.isNotEmpty())
                txt_password.isActivated = true
        }

        txt_name.addTextChangedListener {
            if (txt_name.text.isNotEmpty())
                txt_name.isActivated = true
        }
        txt_email.addTextChangedListener {
            if (txt_email.text.isNotEmpty())
                txt_email.isActivated = true
        }

        txt_confirmPassword.addTextChangedListener {
            if (txt_confirmPassword.text.isNotEmpty())
                txt_confirmPassword.isActivated = true
        }
        //setupRegisterListener()
        //setupGoogleRegisterListener()
        //setupSignInListener()



        //val et_repassword: EditText = findViewById(R.id.et_pass_renter)
        txt_SignIn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        button_register.setOnClickListener {


            val email = txt_email.text.toString()
            val password = txt_password.text.toString()
            val name = txt_name.text.toString()
            val confirmPassword = txt_confirmPassword.text.toString()

            if (name.isEmpty()) {
                txt_name.error = "Name is required"
                txt_name.requestFocus()
                return@setOnClickListener
            }

            if (!name.matches("[a-zA-Z\\s-]+".toRegex())) {
                txt_name.error = "Name can only contain letters, spaces, and hyphens"
                txt_name.requestFocus()
                return@setOnClickListener
            }

            if (name.length > 50) {
                txt_name.error = "Name is too long"
                txt_name.requestFocus()
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                txt_email.error = "Email is required"
                txt_email.requestFocus()
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                txt_email.error = "Invalid email format"
                txt_email.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                txt_password.error = "Password is required"
                txt_password.requestFocus()
                return@setOnClickListener
            }

            if (password.length < 8) {
                txt_password.error = "Password must be at least 8 characters long"
                txt_password.requestFocus()
                return@setOnClickListener
            }

            if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}$".toRegex())) {
                txt_password.error = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character"
                txt_password.requestFocus()
                return@setOnClickListener
            }

            if (confirmPassword.isEmpty()) {
                txt_confirmPassword.error = "Confirm password is required"
                txt_confirmPassword.requestFocus()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                txt_confirmPassword.error = "Passwords do not match"
                txt_confirmPassword.requestFocus()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }

    }
}








