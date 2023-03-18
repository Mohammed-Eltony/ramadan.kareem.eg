package com.ramadan.eg.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ramadan.kareem.eg.Activity.RegisterActivity
import com.ramadan.kareem.eg.R


@SuppressLint("CustomSplashScreen")
@Suppress("DEPRECATION")
class SplashScreenActivity : AppCompatActivity() {





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        visitors()











        Handler().postDelayed({
            iii()
        }, 3000)


    }




    fun visitors() {


        val db = Firebase.firestore

        db.collection("visitors")
            .document("visitors")
            .get()
            .addOnSuccessListener { result ->
                val document = result



                val user = hashMapOf(
                    "visitors" to document.data?.get("visitors").toString().toInt() + 1,
                )

                db.collection("visitors").document("visitors")
                    .set(user).addOnSuccessListener {

                    }

            }


    }
    fun iii(){
        startActivity(Intent(this, RegisterActivity::class.java))
        finish()
    }


}