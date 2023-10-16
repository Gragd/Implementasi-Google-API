package com.example.implementasisigningoogle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity2 : AppCompatActivity() {

    private lateinit var gso: GoogleSignInOptions
    private lateinit var gsc: GoogleSignInClient
    private lateinit var btnLogout: Button
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        gsc = GoogleSignIn.getClient(this, gso)

        val account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)

        if (account != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            supportActionBar?.setDisplayShowTitleEnabled(false)

            toolbar.findViewById<TextView>(R.id.toolbar_title).text = account.displayName
        } else {
            goSignOut()
        }

        btnLogout = findViewById(R.id.btn_logout)

        btnLogout.setOnClickListener {
            goSignOut()
        }

        val profileImage: ImageView = findViewById(R.id.profile_image)
        val photoUrl = account?.photoUrl
        if (photoUrl != null) {
            Glide.with(this)
                .load(photoUrl)
                .circleCrop()
                .into(profileImage)
        } else {
            profileImage.setImageResource(R.drawable.ikon_default_profile)
        }


        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->

            googleMap.uiSettings.isZoomControlsEnabled = true
            googleMap.uiSettings.isMyLocationButtonEnabled = true

            /**
             * Manipulates the map once available.
             * This callback is triggered when the map is ready to be used.
             * This is where we can add markers or lines, add listeners or move the camera. In this case,
             * we just add a marker near Sydney, Australia.
             * If Google Play services is not installed on the device, the user will be prompted to install
             * it inside the SupportMapFragment. This method will only be triggered once the user has
             * installed Google Play services and returned to the app.
             */

            val jakarta = LatLng(-6.200000, 106.816666)
            googleMap.addMarker(MarkerOptions().position(jakarta).title("Marker in Jakarta"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(jakarta, 12f))
        }


    }


    private fun goSignOut() {
        gsc.signOut().addOnSuccessListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
