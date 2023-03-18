package com.ramadan.kareem.eg.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ramadan.kareem.eg.R


class AdminActivity : AppCompatActivity() {

    private lateinit var edt_email : EditText
    private lateinit var edt_data : EditText
    lateinit var text: String
    lateinit var tv_num_visitors : TextView

    lateinit var tv_num_card :TextView
    lateinit var user_add_card: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        edt_email=findViewById(R.id.edt_email)

        tv_num_visitors=findViewById(R.id.tv_num_visitors)
        tv_num_card=findViewById(R.id.tv_num_card)
        user_add_card=findViewById(R.id.user_add_card)
        visitors()
        card()


        // المستخد الذي يمكنه اضافة كرت
        user_add_card.setOnClickListener {
            val database = Firebase.database
            val myRef = database.getReference("user_add_card").push()
            val userData = hashMapOf(
               "key" to myRef.key,
                "verification" to "fouls",
                "email" to edt_email.text.toString())


            myRef.setValue(userData ).addOnSuccessListener {

            }

        }



    }


    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@AdminActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

  // لختيار الرتبه



    fun btn_promotion(view: View) {
        setData()
    }



    fun setData(){
// لمعرفت الايدي الخاص بالمستخدم
        val emeil = Firebase.firestore
        emeil.collection("email").document(edt_email.text.toString())

            .get()
            .addOnSuccessListener{result ->
                val  document = result


                //تحديث بينات الرتبه
                val fruitsDB = Firebase.firestore
                fruitsDB.collection("users").document(document.data?.get("id").toString())

                    .update("rank",text).addOnSuccessListener {
                        Toast.makeText(this@AdminActivity,"text", Toast.LENGTH_SHORT)
                            .show()
                    }

                fruitsDB.collection("users").document(document.data?.get("id").toString())
                .update("date" , edt_data.text.toString()).addOnSuccessListener {

                    }

            }





    }







    //عدد الزوار
    fun visitors() {


        val db = Firebase.firestore

        db.collection("visitors")
            .document("visitors")
            .get()
            .addOnSuccessListener { result ->
                val document = result


                tv_num_visitors.text = document.data?.get("visitors").toString()


            }

    }
    //عدد الكروت  المشحونة
    fun card() {

        val db = Firebase.firestore
        db.collection("card")
            .document("card")
            .get()
            .addOnSuccessListener { result ->
                val document = result
                tv_num_card.text = document.data?.get("card").toString()
            }



    }





    //("yyyy/MM/dd HH:mm:ss")







}