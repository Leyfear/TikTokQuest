package com.example.tiktokrepost.ui.onboarding

import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder

class ViewPagerAdapter(
    list : ArrayList<Fragment>,
    fm :FragmentManager,
    lifecyle: Lifecycle
): FragmentStateAdapter(fm,lifecyle) {
   private val fragmentList : ArrayList<Fragment> = list



    override fun getItemCount(): Int {
       return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

}