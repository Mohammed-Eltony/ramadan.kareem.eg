package com.ramadan.kareem.eg.Activity



import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ramadan.kareem.eg.R
import com.squareup.picasso.Picasso

class ProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore
    lateinit var name: TextView
    lateinit var email: TextView
    lateinit var date: TextView
    lateinit var rank: TextView
    lateinit var id: TextView
    lateinit var points: TextView
    lateinit var imageProfile: ImageView
    lateinit var container_layout: ConstraintLayout
    lateinit var sign_out: Button

    //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        name = findViewById(R.id.name)
        email = findViewById(R.id.email)
        date = findViewById(R.id.date)
        rank = findViewById(R.id.rank)
        points = findViewById(R.id.points)
        id = findViewById(R.id.id)
        imageProfile = findViewById(R.id.imageProfile)
        container_layout = findViewById(R.id.container_layout)
        sign_out = findViewById(R.id.sign_out)
        auth = Firebase.auth


        getData()
    }


    fun getData() {
        val currentUser = auth.currentUser
        db.collection("users")
            .document(currentUser!!.uid)
            .get()
            .addOnSuccessListener { result ->
                val document = result
                name.text = document.data?.get("name").toString()
                email.text = document.data?.get("email").toString()
                rank.text = document.data?.get("rank").toString()
                date.text = document.data?.get("date").toString()

                points.text = document.data?.get("points").toString()
                id.text = document.data?.get("id").toString()
                Picasso.get()
                    .load(document.data?.get("imageProfil").toString())
                    .placeholder(R.drawable.programmer)
                    .error(R.drawable.programmer)
                    .into(imageProfile)

            }
    }

    fun sign_out(view: View) {
        auth = Firebase.auth
        Firebase.auth.signOut()
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }




}