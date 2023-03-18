package com.ramadan.kareem.eg.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ramadan.kareem.eg.Fragment.SignInFragment
import com.ramadan.kareem.eg.R


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


            val fragmentManager = supportFragmentManager
            val fragmentTransition = fragmentManager.beginTransaction()
            fragmentTransition.replace(R.id.fragment, SignInFragment())
            fragmentTransition.commit()







    }

}