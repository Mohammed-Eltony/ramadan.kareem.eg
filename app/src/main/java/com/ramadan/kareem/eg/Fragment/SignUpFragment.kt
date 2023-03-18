package com.ramadan.kareem.eg.Fragment


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ramadan.kareem.eg.Activity.HomeActivity
import com.ramadan.kareem.eg.R

import java.text.SimpleDateFormat
import java.util.*

class SignUpFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var signUp: Button
    private lateinit var text: TextView
    private lateinit var i_have_an_account: TextView






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth



    }



    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)

        name = view.findViewById(R.id.edit_text_name)
        email = view.findViewById(R.id.edit_text_gmail)
        password = view.findViewById(R.id.edit_text_password)
        text = view.findViewById(R.id.text)
        i_have_an_account = view.findViewById(R.id.i_have_an_account)


        signUp = view.findViewById(R.id.sign_up)
        signUp.setOnClickListener {
            signUp(name.text.toString(),email.text.toString(),password.text.toString(),)
        }

        i_have_an_account.setOnClickListener {
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment, SignInFragment())
            transaction?.commit()
        }

        return view
    }












    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            val user = auth.currentUser
            if (user?.isEmailVerified == true){
                val intent = Intent(context, HomeActivity::class.java)
                startActivity(intent)
            }
        }
    }








    fun signUp(name:String,gmail:String,password:String){


        auth.createUserWithEmailAndPassword( gmail,password )
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(context,"تم انشاء ",Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser

                    user!!.sendEmailVerification()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                text.text="⚠️ تم ارسال رابط تفعيل حسابك الي بريدك الالكتروني قم بدخول الي البريد الخاص بك وضغط ففوق الرابط لتفعيل الحسابك شكرا لكم "
                                if (user.isEmailVerified == true){
                                    val intent = Intent(context, HomeActivity::class.java)
                                    startActivity(intent)
                                }
                            }
                        }
//
                    val db = Firebase.firestore
                    val sdf = SimpleDateFormat("dd")
                    val currentDate = sdf.format(Date())

                    val userData = hashMapOf(
                        "name" to "$name",
                        "email" to "$gmail",
                        "password" to "$password",
                        "id" to "${user?.uid}",
                        "points" to "0",
                        "date" to "0",
                        "dateD" to currentDate,
                        "card" to "5",
                        "rank" to "member",
                        "verification" to "fouls",
                        "imageProfil" to "https://c.top4top.io/p_2513lclde1.jpg")

                    db.collection("users").document(user.uid)
                        .set(userData).addOnSuccessListener {
                            val emeil = Firebase.firestore
                            val userEmail = hashMapOf(
                                "id" to "${user?.uid}")
                            emeil.collection("email").document(user.email.toString())
                                .set(userEmail).addOnSuccessListener {


                                }
                        }





                } else {
                    // If sign in fails, display a message to the user.
                    text.text=task.exception?.message
                }
            }




    }






}






