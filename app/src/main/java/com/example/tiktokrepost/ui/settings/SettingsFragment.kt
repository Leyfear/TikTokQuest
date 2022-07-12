package com.example.tiktokrepost.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tiktokrepost.R
import com.example.tiktokrepost.adapter.SettingsAdapter
import com.example.tiktokrepost.databinding.FragmentSettingsBinding
import com.example.tiktokrepost.model.Setting


class SettingsFragment : Fragment() {
    private lateinit var binding :FragmentSettingsBinding
    lateinit var list: ArrayList<Setting>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        list = ArrayList<Setting>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        addSetting()
        setAdapter()
        binding.apply {
            settingsBackIcon.setOnClickListener {
                findNavController().navigateUp()
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setAdapter() {
        val settingAdapter = SettingsAdapter(list)
        binding.rcView.apply {
            layoutManager= LinearLayoutManager(context)
            adapter = settingAdapter
            addItemDecoration(VerticalItemDecoration(50))
        }
    }

    private fun addSetting() {
        list.add(Setting("Get Premium",R.drawable.ic_star))
        list.add(Setting("Contact Us", R.drawable.ic_call))
        list.add(Setting("Rate Us", R.drawable.ic_rate))
        list.add(Setting("Term Of Use", R.drawable.ic_term))
        list.add(Setting("Privacy Policy", R.drawable.ic_info))

    }


}