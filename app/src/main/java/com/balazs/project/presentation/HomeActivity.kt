package com.balazs.project.presentation

import Adapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.balazs.project.R
import com.balazs.project.utils.TransparentStatusBarHandler
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity(){


    private lateinit var auth : FirebaseAuth
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerView2: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        TransparentStatusBarHandler.initTransparentStatusBar(window)

        recyclerView = findViewById(R.id.rv_newest)
        recyclerView2 = findViewById(R.id.rv_recomended)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val imageList = MutableList(20){
            R.drawable.mock
        }
        recyclerView2.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = Adapter(imageList)
        recyclerView2.adapter = Adapter(imageList)



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
}


}