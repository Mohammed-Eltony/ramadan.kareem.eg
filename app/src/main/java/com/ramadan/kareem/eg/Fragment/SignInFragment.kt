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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ramadan.kareem.eg.Activity.HomeActivity
import com.ramadan.kareem.eg.Fragment.ResetPasswordFragment
import com.ramadan.kareem.eg.R


class SignInFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var gmail: EditText
    private lateinit var password: EditText
    private lateinit var signIn: Button
    private lateinit var text: TextView
    private lateinit var forgot_your_password: TextView
    private lateinit var create_an_account: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)
        // Inflate the layout for this fragment
        gmail = view.findViewById(R.id.edit_text_gmail)
        password = view.findViewById(R.id.edit_text_password)
        signIn = view.findViewById(R.id.sign_in)
        text = view.findViewById(R.id.text)
        forgot_your_password = view.findViewById(R.id.forgot_your_password)
        create_an_account = view.findViewById(R.id.create_an_account)
        signIn.setOnClickListener {
            signIn(gmail.text.toString(),password.text.toString(),)
        }


        forgot_your_password.setOnClickListener {
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment, ResetPasswordFragment())
            transaction?.commit()
        }


        create_an_account.setOnClickListener {
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment, SignUpFragment())
            transaction?.commit()
        }



        return view
    }

    override fun onStart() {
        super.onStart()

        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            if (currentUser?.isEmailVerified == true){
                val intent = Intent(context, HomeActivity::class.java)
                startActivity(intent)
                activity?.finish()

            }
        }
    }


    private fun signIn(gmail:String,password:String) {

        auth.signInWithEmailAndPassword(gmail, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser

                    if (user?.isEmailVerified == true){
                        val intent = Intent(context, HomeActivity::class.java)
                        startActivity(intent)
                    }else{
                        text.text="يرجا تفعيل حسابك"
                    }




                } else {
                    // If sign in fails, display a message to the user.

                }
            }

    }


}