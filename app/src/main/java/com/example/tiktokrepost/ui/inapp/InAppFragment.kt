package com.example.tiktokrepost.ui.inapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.tiktokrepost.R
import com.example.tiktokrepost.databinding.FragmentInAppBinding
import com.example.tiktokrepost.ui.onboarding.ViewPagerFragment


class InAppFragment : Fragment() {
   private var text1Lock :Boolean = false
    private var text2Lock: Boolean = false
    private lateinit var binding : FragmentInAppBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInAppBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickedHandler()
        closeButtonHandler()
        startButtonHandler()


    }

    private fun startButtonHandler() {
        binding.nextButton.setOnClickListener {
            if(text1Lock && !text2Lock || !text1Lock && text2Lock) {
                inAppFinish()
                findNavController().navigate(R.id.action_inAppFragment_to_repostFragment)
            }else{
                Toast.makeText(context,"Please select a plan.",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun inAppFinish() {
        val sharedPref =requireActivity().getSharedPreferences("inApp", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished",true)
        editor.apply()
    }

    private fun closeButtonHandler() {
        binding.closeButton.setOnClickListener {
            if(text1Lock && !text2Lock || !text1Lock && text2Lock){
                Toast.makeText(context,"Please click start button.",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(context,"Please select a plan.",Toast.LENGTH_LONG).show()
            }
        }
    }

    val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

    @SuppressLint("ResourceAsColor")
    private fun clickedHandler() {

        val darkblue = Color.parseColor("#6474ff")
        val openedblue = Color.parseColor("#beccfe")
        text1Lock = false
        text2Lock = false
        val textOneBackground = binding.offerText1.background as GradientDrawable
        textOneBackground.mutate()
        val textTwoBackground = binding.offerText2.background as GradientDrawable
        textTwoBackground.mutate()

        binding.offerText1.setOnClickListener {
           if(!text1Lock){
               textOneBackground.setStroke(4.dp,darkblue)
               text1Lock = true.also {
                   if (text2Lock){
                       textTwoBackground.setStroke(2.dp, openedblue)
                       text2Lock = false
                   }
               }
           }
        }
        binding.offerText2.setOnClickListener {
            if(!text2Lock){
                textTwoBackground.setStroke(4.dp,darkblue)
                text2Lock = true.also {
                    if(text1Lock){
                        textOneBackground.setStroke(2.dp,openedblue)
                        text1Lock = false
                    }
                }
            }

        }

    }



}