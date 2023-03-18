package com.ramadan.kareem.eg.Fragment

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
import com.ramadan.kareem.eg.R


class ResetPasswordFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var email: EditText
    private lateinit var sand: Button
    private lateinit var text: TextView
    private lateinit var log_in_to_my_account: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_reset_password, container, false)
        // Inflate the layout for this fragment
        email = view.findViewById(R.id.edit_text_gmail)
        text = view.findViewById(R.id.text)
        log_in_to_my_account = view.findViewById(R.id.log_in_to_my_account)
        sand = view.findViewById(R.id.sand)

        sand.setOnClickListener {
            sand(email.text.toString())
        }

        log_in_to_my_account.setOnClickListener {
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment, SignInFragment())
            transaction?.commit()
        }

        return view
    }

    private fun sand(email: String) {

        Firebase.auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                  text.text="تم ارسال رابط تغيير كلمة المرور"
                }
            }



    }


}