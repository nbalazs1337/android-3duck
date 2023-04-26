package com.balazs.project.presentation

import android.R
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.balazs.project.utils.TransparentStatusBarHandler
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import de.hdodenhof.circleimageview.CircleImageView


class HomeActivity : AppCompatActivity(){


    private lateinit var auth : FirebaseAuth
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.balazs.project.R.layout.activity_home)
        TransparentStatusBarHandler.initTransparentStatusBar(window)
        auth = FirebaseAuth.getInstance()



        val email = intent.getStringExtra("email")
        val displayName = intent.getStringExtra("name")
        findViewById<TextView>(com.balazs.project.R.id.txt_name).text = "Hi, ${displayName}"

        findViewById<CircleImageView>(com.balazs.project.R.id.button_signout).setOnClickListener {
            auth.signOut()
            val googleSignInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN)
            googleSignInClient.signOut().addOnCompleteListener(this) {
                startActivity(Intent(this , LoginActivity::class.java))
                finish()
            }
        }

        val drawerLayout = findViewById<DrawerLayout>(com.balazs.project.R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(com.balazs.project.R.id.navigation_view)
        // Call findViewById on the DrawerLayout


        // Pass the ActionBarToggle action into the drawerListener
        //actionBarToggle = ActionBarDrawerToggle(this, drawerLayout, 0, 0)
       // drawerLayout.addDrawerListener(actionBarToggle)

        // Display the hamburger icon to launch the drawer
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Call syncState() on the action bar so it'll automatically change to the back button when the drawer layout is open
//        actionBarToggle.syncState()


        // Call findViewById on the NavigationView

        // Call setNavigationItemSelectedListener on the NavigationView to detect when items are clicked
        TransparentStatusBarHandler.initTransparentStatusBar(window)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.message -> {
                    Toast.makeText(this, "My Profile", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.home -> {
                    Toast.makeText(this, "People", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.message -> {
                    Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> {
                    false
                }
            }
        }


        val navHostFragment =
            supportFragmentManager.findFragmentById(com.balazs.project.R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController
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
                    navController.navigate(com.balazs.project.R.id.chatFragment)
                    //item.setIcon(R.drawable.ic_message)
                    item.setCheckable(true)
                    // switch to Dashboard fragment or activity
                    true
                }
                com.balazs.project.R.id.navigation_worker -> {
                    navController.navigate(com.balazs.project.R.id.settingsFragment)
                    //item.setIcon(R.drawable.ic_settings_green)
                    item.setCheckable(true)
                    // switch to Notifications fragment or activity
                    true
                }
                else -> false
            }
}}}


