package com.ramadan.eg.Adapter

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ramadan.kareem.eg.Data.CardData
import com.ramadan.kareem.eg.R

class CardAdapter(val activity: Activity, val arrayCard: ArrayList<CardData>) :
    RecyclerView.Adapter<CardAdapter.MVH>() {

    private lateinit var database: DatabaseReference
    private lateinit var Stay: TextView
    private var rewardedAd: RewardedAd? = null


    companion object {
        const val REQUEST_CODE = 42
    }

    class MVH(view: View) : RecyclerView.ViewHolder(view) {

        val Stay: TextView = view.findViewById(R.id.Stay)
        val logo: ImageView = view.findViewById(R.id.logo)
        val call: ImageView = view.findViewById(R.id.call)
        val progressBar: ProgressBar = view.findViewById(R.id.ProgressBar)
        val unit: TextView = view.findViewById(R.id.unit)
        val text_col: TextView = view.findViewById(R.id.text_col)
        val watch: TextView = view.findViewById(R.id.watch)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MVH {
        lodAds()
        val view = activity.layoutInflater.inflate(R.layout.custom_list_item, parent, false)
        return MVH(view)
    }

    override fun onBindViewHolder(holder: MVH, position: Int) {
        holder.Stay.text = arrayCard.get(position).stay
        holder.watch.text = arrayCard.get(position).watch
        holder.unit.text = arrayCard.get(position).unit




        when (arrayCard.get(position).company) {
            "VODAFONE" -> {
                holder.logo.setImageResource(R.drawable.vodafone)
            }
            "WE" -> {
                holder.logo.setImageResource(R.drawable.we)
            }
            "ORANGE" -> {
                holder.logo.setImageResource(R.drawable.orange)
            }
            "ETISALAT" -> {
                holder.logo.setImageResource(R.drawable.etisalat)
            }
        }

            holder.call.setOnClickListener {
                if (rewardedAd!=null) {
                chak(position, holder)
                holder.call.visibility = View.INVISIBLE
                holder.progressBar.visibility = View.VISIBLE
                holder.text_col.visibility = View.INVISIBLE
                }
                else{
                    Toast.makeText(this.activity,"جاري تحميل الاعلان....",Toast.LENGTH_SHORT).show()
                }
            }
            holder.text_col.setOnClickListener {
                if (rewardedAd!=null) {
                    chak(position, holder)
                    holder.call.visibility = View.INVISIBLE
                    holder.progressBar.visibility = View.VISIBLE
                    holder.text_col.visibility = View.INVISIBLE
                }
                else{
                    Toast.makeText(this.activity,"جاري تحميل الاعلان....",Toast.LENGTH_SHORT).show()
                }
            }


    }

    override fun getItemCount() = arrayCard.size


    fun actionCall(position: Int, namCard: Int,holder:MVH) {

        val auth: FirebaseAuth = Firebase.auth
        val currentUser = auth.currentUser

        val fruitsDB = Firebase.firestore
        fruitsDB.collection("users").document(currentUser!!.uid)
            .update("card", namCard - 1).addOnSuccessListener {
                holder.call.visibility = View.VISIBLE
                holder.progressBar.visibility = View.INVISIBLE
                holder.text_col.visibility=View.VISIBLE
            }
        card()
        up(position)
        val card = arrayCard[position]
        val intent = Intent(Intent.ACTION_CALL)
        intent.data =
            "tel:${card.rechargeCardPrefix}${card.number.trim()}${Uri.encode(card.rechargeCardPostfix)}".toUri()
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        activity.startActivity(intent)


    }


    fun up(position: Int) {
        val database = Firebase.database
        val myRef = database.getReference("card").child(arrayCard.get(position).key)

        val watch = arrayCard.get(position).watch.toInt() + 1
        val stay = arrayCard.get(position).stay.toInt() - 1
        val dataPost = hashMapOf(
            "key" to arrayCard.get(position).key,
            "number" to arrayCard.get(position).number,
            "stay" to stay.toString(),
            "watch" to watch.toString(),
            "unit" to arrayCard.get(position).unit,
            "company" to arrayCard.get(position).company,
            "rechargeCardPrefix" to arrayCard.get(position).rechargeCardPrefix,
            "rechargeCardPostfix" to arrayCard.get(position).rechargeCardPostfix
        )

        myRef.setValue(dataPost)

    }


    fun card() {


        val db = Firebase.firestore

        db.collection("card")
            .document("card")
            .get()
            .addOnSuccessListener { result ->
                val document = result


                val user = hashMapOf(
                    "card" to document.data?.get("card").toString().toInt() + 1,
                )

                db.collection("card").document("card")
                    .set(user).addOnSuccessListener {

                    }

            }


    }


    fun chak(position: Int,holder:MVH) {
        val db = Firebase.firestore
        val auth: FirebaseAuth = Firebase.auth



        val currentUser = auth.currentUser
        db.collection("users")
            .document(currentUser!!.uid)
            .get()
            .addOnSuccessListener { result ->
                val document = result
                val namCard: Int = document.data?.get("card").toString().toInt()

                if (namCard > 0) {


                    onClik(position, namCard,holder)

                } else if (namCard <= 0) {
                    Toast.makeText(
                        this.activity,
                        "لقد قمت بشحن عدد الكروت اليومي المسموح لك",
                        Toast.LENGTH_SHORT
                    ).show()
                    holder.call.visibility = View.VISIBLE
                    holder.progressBar.visibility = View.INVISIBLE
                    holder.text_col.visibility=View.VISIBLE
                }

            }


    }


    // غند الضغط
    fun onClik(position: Int, namCard: Int,holder:MVH) {
        rewardedAd?.let { ad ->
            ad.show(activity, OnUserEarnedRewardListener { rewardItem ->
                // Handle the reward.
                val rewardAmount = rewardItem.amount
                rewardItem.type
                actionCall(position, namCard,holder)

            })
        } ?: run {
        }
    }


    fun lodAds() {


        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(
            activity,
            "ca-app-pub-8330627366418415/7574379242",
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    rewardedAd = null
                    ads2()
                }

                override fun onAdLoaded(ad: RewardedAd) {
                    rewardedAd = ad
                }
            })


    }


    fun ads2() {
        rewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
                // Called when a click is recorded for an ad.
                Toast.makeText(activity, "Ad was clicked.", Toast.LENGTH_SHORT).show()
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.
                Toast.makeText(activity, "Ad dismissed fullscreen content.", Toast.LENGTH_SHORT)
                    .show()

                rewardedAd = null
                lodAds()
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                // Called when ad fails to show.
                Toast.makeText(
                    activity, "Ad failed to show fullscreen content.", Toast.LENGTH_SHORT
                ).show()

                rewardedAd = null
            }

            override fun onAdImpression() {
                // Called when an impression is recorded for an ad.
                Toast.makeText(activity, "Ad recorded an impression.", Toast.LENGTH_SHORT).show()

            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Toast.makeText(activity, "Ad showed fullscreen content.", Toast.LENGTH_SHORT)
                    .show()

            }
        }
    }


}


