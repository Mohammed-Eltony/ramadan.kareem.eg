package com.ramadan.kareem.eg

import android.content.Context
import android.widget.ArrayAdapter
import com.ramadan.kareem.eg.Data.CardData


class CardAdapter(
    context: Context, cardDataList:
    ArrayList<CardData>
) : ArrayAdapter<CardData>(context, 0, cardDataList) {


}