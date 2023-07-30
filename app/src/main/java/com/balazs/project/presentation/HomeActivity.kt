package com.balazs.project.presentation;

//import com.balazs.project.TestActivity

import AppModule
import android.R
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button

import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.room.Room
import com.balazs.project.persistence.entity.WorkerEntity
import com.balazs.project.persistence.localApi.WorkerApi
import com.balazs.project.utils.TransparentStatusBarHandler
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.launch
import javax.inject.Inject


class HomeActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar
    @Inject
    lateinit var workerApi: WorkerApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.balazs.project.R.layout.activity_home)
        TransparentStatusBarHandler.initTransparentStatusBar(window)
        val appDatabase = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "my-database").build()
        //deleteAllWorkers()
        // Initialize Dagger
        val appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .build()

        // Inject dependencies
        appComponent.inject(this)
        //val workers = workerApi.getAllWorkers()
        // Create a new worker
        /*val worker = WorkerDB(
            name = "Bathroom Tap Changer",
            city = "Cluj-Napoca",
            type = "contract",
            pricePerHour = 20.0
        )
        addWorker(worker)*/

        auth = FirebaseAuth.getInstance()
        val navHostFragment =
            supportFragmentManager.findFragmentById(com.balazs.project.R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val notiButton: ImageButton = findViewById(com.balazs.project.R.id.btn_notifications)
        notiButton.setOnClickListener {
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }

        val user = FirebaseAuth.getInstance().currentUser
        val profilePic = findViewById<CircleImageView>(com.balazs.project.R.id.button_signout)
        if (user != null && user.photoUrl != null) {
            val profilePhotoUrl = user?.photoUrl.toString()

            Glide.with(this)
                .load(profilePhotoUrl)
                .placeholder(com.balazs.project.R.drawable.profile) // Optional placeholder image while loading
                .error(com.balazs.project.R.drawable.profile) // Optional error image if the loading fails
                .circleCrop() // Apply circular cropping to the image
                .into(profilePic)

        }
        else {
            // If the user doesn't have a profile photo, you can set a default image
            profilePic.setImageResource(com.balazs.project.R.drawable.profile)
        }

        val email = user?.email
        val displayName = user?.displayName
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
       val nameTextView =  navigationView.getHeaderView(0).findViewById<TextView>(com.balazs.project.R.id.name)
       val emailTextView =  navigationView.getHeaderView(0).findViewById<TextView>(com.balazs.project.R.id.username)
        nameTextView.text = user?.displayName
        emailTextView.text = user?.email
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                com.balazs.project.R.id.nav_profile -> {

                    val intent = Intent(this, ProfileActivity::class.java)
                    intent.putExtra("name", nameTextView.text)
                    intent.putExtra("email", emailTextView.text)
                    startActivity(intent)

                    true
                }
                com.balazs.project.R.id.nav_chat -> {
                    val intent = Intent(this, FavoriteActivity::class.java)
                    startActivity(intent)
                    true
                }
                com.balazs.project.R.id.nav_settings -> {
                    val intent = Intent(this, NotificationActivity::class.java)
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



    private fun addWorker(worker: WorkerEntity) {
        // Invoke the addWorker method of WorkerApi to add the worker
        lifecycleScope.launch {
            workerApi.addWorker(worker)
        }
    }
    private fun deleteAllWorkers() {
        lifecycleScope.launch {
            workerApi.deleteAllWorkers()
        }
    }
}


