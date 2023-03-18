package com.ramadan.kareem.eg.Data

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.widget.DatePicker
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class CustomDatePickerFragment : DialogFragment(),DatePickerDialog.OnDateSetListener {
//
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val  calendar : Calendar = Calendar.getInstance()
        val yeae = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(activity!!,AlertDialog.THEME_HOLO_LIGHT,this,yeae,month,day)
        val textView = TextView(activity)
        val layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT)
        textView.layoutParams= layoutParams
        textView.setPadding(20,20,20,20)
        textView.gravity=Gravity.CENTER
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,25f)
        textView.setText("This is Custom title")
        textView.setTextColor(Color.parseColor("#ffffff"))
        textView.setBackgroundColor(Color.parseColor("#5Ac15e"))
        datePickerDialog.setCustomTitle(textView)
        return datePickerDialog
    }



    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        TODO("Not yet implemented")
    }


}