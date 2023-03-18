package com.ramadan.kareem.eg.Activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.onesignal.OneSignal

import com.ramadan.eg.Adapter.CardAdapter
import com.ramadan.kareem.eg.Data.CardData
import com.ramadan.kareem.eg.Data.DataUesr

import com.ramadan.kareem.eg.R
import com.ramadan.kareem.eg.UpdateActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore
    private lateinit var recyclerView: RecyclerView
    lateinit var navigationView: NavigationView
    lateinit var totalCard: TextView
    lateinit var drawerLayout: DrawerLayout
    lateinit var shweNav : ImageButton
    lateinit var hider : LinearLayout
    private  var numCard =0
     val ONESIGNAL_APP_ID = "01bfa2ef-0385-4c0b-bb0d-1eb283dcd6eb"
    lateinit var mAdView : AdView
    private var linkApp="https://t.me/confgat_egypt"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        namberCard()

        recyclerView = findViewById(R.id.RecyclerView)
        navigationView = findViewById(R.id.navigationView)
        drawerLayout = findViewById(R.id.drawerLayout)
        totalCard = findViewById(R.id.totalCard)
        shweNav = findViewById(R.id.shweNav)
        hider = findViewById(R.id.hider)
        update(this)
        date()
        navigationView()
        getData()
        getDataUser()


        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)



        //اتصنط علي الكلك في المنيو
        navigationView.setNavigationItemSelectedListener(this)


        shweNav.setOnClickListener {
            openDrawer()
        }



        // Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)

    }

    private fun update(homeActivity: HomeActivity) {
        val database = Firebase.database
        val myRef = database.getReference("settingsUpdate")

        myRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.value
                if (value.toString()=="0"){

                }else{
                    startActivity(Intent(homeActivity, UpdateActivity::class.java))
                    finish()

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }


    override fun onStart() {
        super.onStart()
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(android.Manifest.permission.CALL_PHONE),
                CardAdapter.REQUEST_CODE
            )

        }

    }


    private fun getData() {

        val database = Firebase.database
        val myRef = database.getReference("card")

        myRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val arrayCardData = ArrayList<CardData>()
                for (n in snapshot.children) {
                    val cardData = n.getValue(CardData::class.java)
                    if (cardData?.stay?.toInt()!! > 0){
                        arrayCardData.add(cardData!!)
                    }else{
                        myRef.child(cardData.key).removeValue()
                    }
                }


                val cardAdapter = CardAdapter(this@HomeActivity, arrayCardData)
                recyclerView.layoutManager = LinearLayoutManager(this@HomeActivity)
                recyclerView.setHasFixedSize(true)
                recyclerView.adapter = cardAdapter


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


    }


    fun navigationView() {

        var actionTooggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.drawe_Open,
            R.string.drawer_clos
        )
        drawerLayout.addDrawerListener(actionTooggle)
        actionTooggle.syncState()
    }


    override fun onNavigationItemSelected(p0: MenuItem): Boolean {

        val item = p0.title


        if (item == "Account" || item == "حسابي"){
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        else if (item == "Admin" || item == "ادمن"){
            admin()
        }
        else if (item == "Add Cradet" || item == "اضافه كارت"){
            add_Cradet()
        }
        else if (item == "Who Are We" || item == "من نحن"){
            startActivity(Intent(this, WhoAreWeActivity::class.java))
        }
        else if (item == "sharing" || item == "مشاركة"){
            sharingApp()
        }
        else if (item == "Logo Ut" || item == "خروج"){
            logoUt()
        }
//        when(item){
//           "Account"->{
//               val intent = Intent(this, ProfileActivity::class.java)
//               startActivity(intent)
//           }
//            "Admin"->{
//                admin()
//            }
//            "Add Cradet"->{
//                add_Cradet()
//            }
//
//            "Who Are We"->{
//                startActivity(Intent(this, WhoAreWeActivity::class.java))
//            }
//
//
//
//
//
//            "Logo Ut"->{
//                logoUt()
//            }
//        }



        // اغلاق بعد الضغط
        closDrawer()
        return true
    }


    fun sharingApp(){

        val appMsg :String = "مرحبا، أنا أستخدم اشحن ، انضم إلي! قم بتنزيله من هنا:" +"https://play.google.com/store/apps/details?id=com.ramadan.kareem.eg"
        val intent = Intent()
        intent.action =Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT,appMsg)
        intent.type = "text/plain"
        startActivity(intent)

    }


    private fun closDrawer() {
        drawerLayout.closeDrawer(GravityCompat.END)
    }

    private fun openDrawer(){
        drawerLayout.openDrawer(GravityCompat.END)
    }


    fun admin(){
          var auth = Firebase.auth
        val currentUser = auth.currentUser
        if (currentUser?.email.equals("hamdyfathy010@gmail.com")||currentUser?.email.equals("mohammed.badawi.1001@gmail.com")){
            val intent = Intent(this, AdminActivity::class.java)
            startActivity(intent)
        }
    }

    fun add_Cradet(){
        var auth = Firebase.auth
        val currentUser = auth.currentUser
        val database = Firebase.database
        val myRef = database.getReference("user_add_card")


        myRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                for (n in snapshot.children) {
                    val dataUesr = n.getValue(DataUesr::class.java)
                    if (dataUesr?.email.equals(currentUser?.email)) {
                        val intent = Intent(this@HomeActivity, AddCradetActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                }


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })



    }


    fun logoUt(){
        Firebase.auth.signOut()
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }



    fun getDataUser() {

        Handler().postDelayed({
            auth = Firebase.auth

            val currentUser = auth.currentUser
            db.collection("users")
                .document(currentUser!!.uid)
                .get()
                .addOnSuccessListener { result ->
                    val document = result
                    totalCard.text = document.data?.get("card").toString()


                }
            getDataUser()
        },1000)


    }


    fun date(){

        var auth: FirebaseAuth
        val db = Firebase.firestore
        auth = Firebase.auth
        val currentUser = auth.currentUser
        db.collection("users")
            .document(currentUser!!.uid)
            .get()
            .addOnSuccessListener { result ->
                val document = result

                val sdfD = SimpleDateFormat("dd")
                val currentDateD = sdfD.format(Date())

                val m = document.data?.get("date").toString().toInt()



                if (currentDateD.toInt()>document.data?.get("dateD").toString().toInt()){

                    val fruitsDB = Firebase.firestore
                    fruitsDB.collection("users").document(currentUser!!.uid)
                        .update("dateD",currentDateD.toInt()).addOnSuccessListener {

                        }




                    fruitsDB.collection("users").document(currentUser!!.uid,)
                        .update("card",numCard).addOnSuccessListener {

                        }

                    fruitsDB.collection("users").document(currentUser!!.uid,)
                        .update("date", m-1).addOnSuccessListener {

                        }

                }


                }




            }




    fun date1() : Int{

        val day = SimpleDateFormat("dd")
        val yer = SimpleDateFormat("yyyy")
        val month = SimpleDateFormat("MM")
        val currentDay = day.format(Date()).toInt()
        val currentYer = yer.format(Date()).toInt()
        val currentMonth = month.format(Date()).toInt()

        val days :Int =currentDay+(currentYer*360)+(currentMonth*30)
        return days

    }



    fun namberCard() {
        var auth: FirebaseAuth
        val db = Firebase.firestore
        db.collection("numCard")
            .document("numCard")
            .get()
            .addOnSuccessListener { result ->
                val document = result

                numCard = document.data?.get("num").toString().toInt()


            }


    }




}
//∞