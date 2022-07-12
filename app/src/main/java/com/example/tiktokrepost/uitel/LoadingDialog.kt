package com.example.tiktokrepost.uitel

import android.app.Activity
import android.app.AlertDialog
import com.example.tiktokrepost.R

class LoadingDialog(val mActivitiy: Activity) {
    private lateinit var isDialog: AlertDialog
    fun startLoading(){
        //set view
        val inflater = mActivitiy.layoutInflater
        val dialogView = inflater.inflate(R.layout.loading_layout,null)
        //set dialog
        val builder = AlertDialog.Builder(mActivitiy)
        builder.setView(dialogView)
        builder.setCancelable(false)
        isDialog = builder.create()
        isDialog.show()
    }
    fun isDismiss(){
        isDialog.dismiss()
    }
}