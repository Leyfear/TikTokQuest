package com.example.tiktokrepost.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tiktokrepost.databinding.SettingsRowBinding
import com.example.tiktokrepost.model.Setting

class SettingsAdapter(val list : ArrayList<Setting>) : RecyclerView.Adapter<SettingsAdapter.SettingsHolder>() {
    class SettingsHolder(val binding : SettingsRowBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(setting : Setting){
            binding.tvNoteTitle.text = setting.settingName
            binding.tvNotImage.setImageResource(setting.imageRes)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsHolder {
        val binding = SettingsRowBinding.inflate(LayoutInflater.from(parent.context))
        return SettingsHolder(binding)
    }

    override fun onBindViewHolder(holder: SettingsHolder, position: Int) {
        val setting = list[position]
        holder.bind(setting)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}