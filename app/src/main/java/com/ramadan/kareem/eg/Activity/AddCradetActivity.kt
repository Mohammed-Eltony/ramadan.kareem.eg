package com.ramadan.kareem.eg.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ramadan.kareem.eg.Data.RechargeCompanies
import com.ramadan.kareem.eg.R


class AddCradetActivity : AppCompatActivity() {

    lateinit var card_number: EditText
    lateinit var number_of_shipments: EditText
    lateinit var unit: EditText
    lateinit var text: String
    lateinit var bottom_share: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_cradet)

        bottom_share = findViewById(R.id.bottom_share)
        unit = findViewById(R.id.unit)
        card_number = findViewById(R.id.card_number)
        number_of_shipments = findViewById(R.id.number_of_shipments)
        number_of_shipments = findViewById(R.id.number_of_shipments)
        spinner()
        setData()


    }


    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@AddCradetActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }


    private fun setData() {

        bottom_share.setOnClickListener {
            val company: RechargeCompanies = when (text) {
                RechargeCompanies.VODAFONE.companyName -> RechargeCompanies.VODAFONE
                RechargeCompanies.ETISALAT.companyName -> RechargeCompanies.ETISALAT
                RechargeCompanies.WE.companyName -> RechargeCompanies.WE
                RechargeCompanies.ORANGE.companyName -> RechargeCompanies.ORANGE
                else -> RechargeCompanies.VODAFONE
            }
            val database = Firebase.database
            val myRef = database.getReference("card").push()
            val dataPost = hashMapOf(
                "key" to myRef.key,
                "number" to card_number.text.toString(),
                "stay" to number_of_shipments.text.toString(),
                "watch" to "0",
                "unit" to unit.text.toString(),
                "company" to company.name,
                "rechargeCardPrefix" to company.chargePrefix,
                "rechargeCardPostfix" to company.chargePostfix
            )

            if (card_number.text.isNotEmpty() && number_of_shipments.text.isNotEmpty() && unit.text.isNotEmpty()) {
                myRef.setValue(dataPost)
            } else {
                if (card_number.text.isEmpty())
                    card_number.error = "انهو فارغ "
                else if (number_of_shipments.text.isEmpty())
                    number_of_shipments.error = "انهو فارغ "
                else {
                    number_of_shipments.error = "انهو فارغ "
                    card_number.error = "انهو فارغ "
                }
            }

        }

    }


    fun spinner() {
        val spinner = findViewById<Spinner>(R.id.spinner)
        val fruits = arrayOf("vodafone", "orange", "we", "etisalat")
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, fruits)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                text = p0?.getItemAtPosition(p2).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {


            }
        }
    }




}

