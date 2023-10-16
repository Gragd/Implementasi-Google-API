@file:Suppress("DEPRECATION")

package com.example.implementasisigningoogle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class MainActivity : AppCompatActivity() {

    private lateinit var googleSignIn:SignInButton
    private lateinit var gso:GoogleSignInOptions
    private lateinit var gsc:GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        googleSignIn= findViewById(R.id.sign_in_button)

        gso=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        gsc=GoogleSignIn.getClient(this,gso)

        val account: GoogleSignInAccount?= GoogleSignIn
            .getLastSignedInAccount(this)

        if(account!=null){

            goToMain()
        }

        googleSignIn.setOnClickListener{

            goToSignIn()
        }
    }

    private fun goToSignIn() {

        val signInIntent=gsc.signInIntent

        startActivityForResult(signInIntent, 1000)


    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==1000){

            val task: Task<GoogleSignInAccount> = GoogleSignIn
                .getSignedInAccountFromIntent(data)

            try {

                task.getResult(ApiException::class.java)

                goToMain()
            } catch (e: ApiException) {
                // handle error
            }
        }
    }

    private fun goToMain() {

        val intent= Intent(this, MainActivity2::class.java)

        startActivity(intent)

        finish()

    }
}