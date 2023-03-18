package com.ramadan.kareem.eg

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ramadan.kareem.eg.Activity.HomeActivity
import com.ramadan.kareem.eg.R

class UpdateActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        update(this)


    }







    fun update(activity: UpdateActivity){
        val database = Firebase.database
        val myRef = database.getReference("settingsUpdate")

        myRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.value
                if (value.toString()=="0"){
                    intent()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun intent(){
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }




}