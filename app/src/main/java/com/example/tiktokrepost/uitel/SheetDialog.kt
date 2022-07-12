package com.example.tiktokrepost.uitel

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.tiktokrepost.R

class SheetDialog(val context: Context, val activity: Activity) {
    private lateinit var dialog: Dialog
   lateinit var next : TextView
   lateinit var button :ImageView
   lateinit var linkText: EditText
    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.Q)
    fun setDialog(){
        dialog = Dialog(context)
        val inflater = activity.layoutInflater
        val dialogView = inflater.inflate(R.layout.bottomsheetlayout, null)
        dialog.setContentView(dialogView)
        button = dialog.findViewById<ImageView>(R.id.imageView)
        next = dialog.findViewById<TextView>(R.id.nextButton)
        linkText = dialog.findViewById<EditText>(R.id.pasteLink)
        val underlineText = dialog.findViewById<TextView>(R.id.textView2)
        underlineText.paint.isUnderlineText = true
        underlineText.paint.underlineColor = R.color.black
        if (dialog.window != null) {
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            //dialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            dialog.window!!.attributes.windowAnimations = R.style.DialoAnimation
            //dialog.window!!.attributes.blurBehindRadius = 30
            dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                dialog.window!!.attributes.blurBehindRadius = 60
            }
            dialog.window!!.setGravity(Gravity.BOTTOM)

            //dialog.window!!.setDimAmount(0.8f)
            //val deger = dialog.window!!.attributes
            ;
            //dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
            //deger.flags = WindowManager.LayoutParams.FLAG_BLUR_BEHIND
            //dialog.window!!.attributes = deger


        }

    }

    fun showDialog(){
        dialog.show()
    }
    fun dissmissDialog(){
        dialog.dismiss()
    }

}