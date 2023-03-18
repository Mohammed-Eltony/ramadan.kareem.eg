package com.ramadan.kareem.eg.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.ramadan.kareem.eg.R


class WhoAreWeActivity : AppCompatActivity() {

    @SuppressLint("WrongViewCast", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_who_are_we)
        var link_telegram = findViewById<ImageView>(R.id.link_telegram)
        var link_facebook = findViewById<ImageView>(R.id.link_facebook)

        link_telegram.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://t.me/confgat_egypt")
            startActivity(intent)
        }
        link_facebook.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://web.facebook.com/profile.php?id=100070708845279")
            startActivity(intent)
        }
    }




  /*  override fun onBackPressed() {
        val intent = Intent(this@WhoAreWeActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }*/

}