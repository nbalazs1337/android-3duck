package com.balazs.project.presentation

import Adapter
import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.balazs.project.R
import com.balazs.project.data.model.Data
import com.balazs.project.utils.TransparentStatusBarHandler
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity(){


    private lateinit var auth : FirebaseAuth

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        TransparentStatusBarHandler.initTransparentStatusBar(window)
        auth = FirebaseAuth.getInstance()



        val email = intent.getStringExtra("email")
        val displayName = intent.getStringExtra("name")
        findViewById<TextView>(R.id.txt_name).text = "Hello ${displayName}"

        findViewById<Button>(R.id.button_signout).setOnClickListener {
            auth.signOut()
            val googleSignInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN)
            googleSignInClient.signOut().addOnCompleteListener(this) {
                startActivity(Intent(this , LoginActivity::class.java))
                finish()
            }
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavigationView = findViewById(R.id.bottom_navigation_view)
        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    navController.navigate(R.id.homeFragment)
                    item.setIcon(R.drawable.ic_home_green)
                    item.setCheckable(true)
                    // switch to Home fragment or activity
                    true
                }
                R.id.navigation_chat -> {
                    navController.navigate(R.id.chatFragment)
                    item.setIcon(R.drawable.ic_message)
                    item.setCheckable(true)
                    // switch to Dashboard fragment or activity
                    true
                }
                R.id.navigation_settings -> {
                    navController.navigate(R.id.settingsFragment)
                    item.setIcon(R.drawable.ic_settings_green)
                    item.setCheckable(true)
                    // switch to Notifications fragment or activity
                    true
                }
                else -> false
            }
}}}


