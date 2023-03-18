package com.ramadan.kareem.eg.Data

data class CardData(
    val key: String,
    val unit: String,
    val number: String,
    val stay: String,
    val watch: String,
    val company: String,
    val rechargeCardPrefix: String,
    val rechargeCardPostfix: String = "#"
) {

    constructor() : this("", "", "", "", "", "", "")


}