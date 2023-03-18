package com.ramadan.kareem.eg.Data

enum class RechargeCompanies(val companyName: String, val chargePrefix: String, val chargePostfix: String) {
    VODAFONE("vodafone", "*858*", "#"),
    WE("we", "*555*", "#"),
    ETISALAT("etisalat", "*556*", "#"),
    ORANGE("orange", "*5050*", "#")

}
