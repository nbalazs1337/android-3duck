package com.balazs.project.presentation

import android.R
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View

import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.balazs.project.utils.TransparentStatusBarHandler
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import de.hdodenhof.circleimageview.CircleImageView

class HomeActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.balazs.project.R.layout.activity_home)
        TransparentStatusBarHandler.initTransparentStatusBar(window)
        auth = FirebaseAuth.getInstance()
        val navHostFragment =
            supportFragmentManager.findFragmentById(com.balazs.project.R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val notiButton: ImageButton = findViewById(com.balazs.project.R.id.btn_notifications)
        notiButton.setOnClickListener {
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }

        val test_text :TextView = findViewById(com.balazs.project.R.id.txt_name)
        test_text.setOnClickListener {
            val intent = Intent(this, TestActivity::class.java)
            startActivity(intent)
        }


        val email = intent.getStringExtra("email")
        val displayName = intent.getStringExtra("name")
        findViewById<TextView>(com.balazs.project.R.id.txt_name).text = "Hi, ${displayName}"

        findViewById<CircleImageView>(com.balazs.project.R.id.button_signout).setOnClickListener {
            auth.signOut()
            val googleSignInClient =
                GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN)
            googleSignInClient.signOut().addOnCompleteListener(this) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }


        toolbar = findViewById(com.balazs.project.R.id.toolbar)
        setSupportActionBar(toolbar)
        drawerLayout = findViewById(com.balazs.project.R.id.drawer_layout)
        actionBarToggle = ActionBarDrawerToggle(
            this,
            drawerLayout, toolbar,
            R.string.cut,
            R.string.cancel
        ).apply {
            drawerArrowDrawable.color =
                ContextCompat.getColor(this@HomeActivity, com.balazs.project.R.color.app_green)
            setHomeAsUpIndicator(com.balazs.project.R.drawable.ic_hamburger)
        }
        drawerLayout.addDrawerListener(actionBarToggle)
        actionBarToggle.syncState()
        navigationView = findViewById(com.balazs.project.R.id.navigation_view)
        drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                // No implementation needed
            }

            override fun onDrawerOpened(drawerView: View) {
                // Set the status bar color to a desired color when the drawer is opened
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = ContextCompat.getColor(
                        this@HomeActivity,
                        com.balazs.project.R.color.app_green
                    )
                }
            }

            override fun onDrawerClosed(drawerView: View) {
                // Set the status bar color to a transparent color when the drawer is closed
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = ContextCompat.getColor(
                        this@HomeActivity,
                        com.balazs.project.R.color.app_green
                    )
                }
            }

            override fun onDrawerStateChanged(newState: Int) {
                // No implementation needed
            }
        })

        // Call setNavigationItemSelectedListener on the NavigationView to detect when items are clicked
        TransparentStatusBarHandler.initTransparentStatusBar(window)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                com.balazs.project.R.id.nav_profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)

                    true
                }
                com.balazs.project.R.id.nav_chat -> {
                    val intent = Intent(this, MessagesActivity::class.java)
                    startActivity(intent)
                    true
                }
                com.balazs.project.R.id.nav_settings -> {
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                    true
                }
                com.balazs.project.R.id.nav_share -> {
                    true
                }
                else -> {
                    false
                }
            }
        }



        bottomNavigationView = findViewById(com.balazs.project.R.id.bottom_navigation_view)
        bottomNavigationView.itemIconTintList = null
        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                com.balazs.project.R.id.navigation_tenant -> {
                    navController.navigate(com.balazs.project.R.id.homeFragment)
                    //item.setIcon(R.drawable.ic_home_green)
                    item.setCheckable(true)
                    // switch to Home fragment or activity
                    true
                }
                com.balazs.project.R.id.navigation_landlord -> {
                    navController.navigate(com.balazs.project.R.id.landlordFragment)
                    //item.setIcon(R.drawable.ic_message)
                    item.setCheckable(true)
                    // switch to Dashboard fragment or activity
                    true
                }
                com.balazs.project.R.id.navigation_worker -> {
                    navController.navigate(com.balazs.project.R.id.workerFragment)
                    //item.setIcon(R.drawable.ic_settings_green)
                    item.setCheckable(true)
                    // switch to Notifications fragment or activity
                    true
                }
                else -> false
            }
        }

    }

    private fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp.toFloat() * density + 0.5f).toInt()
    }
}


