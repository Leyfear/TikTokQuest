package com.example.tiktokrepost.ui.onboarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.tiktokrepost.R
import com.example.tiktokrepost.databinding.FragmentViewPagerBinding
import com.example.tiktokrepost.ui.onboarding.screens.FirstScreen
import com.example.tiktokrepost.ui.onboarding.screens.SecondScreen
import com.example.tiktokrepost.ui.onboarding.screens.ThirdScreen


class ViewPagerFragment : Fragment() {

    private lateinit var binding : FragmentViewPagerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentViewPagerBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(onBoardingFinished() && inAppFinished()){
            findNavController().navigate(R.id.action_viewPagerFragment_to_repostFragment)
        } else if(onBoardingFinished()){
            findNavController().navigate(R.id.action_viewPagerFragment_to_inAppFragment)
        }
        var count : Int = 0
        val fragmentList = arrayListOf<Fragment>(
            FirstScreen(),
            SecondScreen(),
            ThirdScreen()
        )
        val viewpager = binding.viewPager

        binding.nextButton.setOnClickListener {
            count = viewpager.currentItem
            if(count == 2){
                onBoardingFinish()
                findNavController().navigate(R.id.action_viewPagerFragment_to_inAppFragment)

            }
            count++
            viewpager.currentItem = count
        }

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        binding.viewPager.adapter = adapter
        val indicator = binding.indicator
        indicator.setViewPager(binding.viewPager)
    }



    private fun onBoardingFinish() {
        val sharedPref =requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished",true)
        editor.apply()
    }
    private fun onBoardingFinished() : Boolean {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished",false)
    }
    private fun inAppFinished() : Boolean {
        val sharedPref = requireActivity().getSharedPreferences("inApp", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished",false)
    }


}