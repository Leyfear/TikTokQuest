package com.example.tiktokrepost.ui.repost

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.tiktokrepost.R
import com.example.tiktokrepost.databinding.FragmentShareBinding
import com.example.tiktokrepost.db.VideoResponse
import com.example.tiktokrepost.model.VideoInf
import com.example.tiktokrepost.network.ResponseModel
import com.example.tiktokrepost.uitel.LoadingDialog
import com.example.tiktokrepost.viewmodel.ReposViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class ShareFragment : Fragment() {
    private var videoInf : VideoInf? = null
    private lateinit var loadingDialog: LoadingDialog
    private val viewModel: ReposViewModel by sharedViewModel()
    private lateinit var binding : FragmentShareBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentShareBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingDialog = LoadingDialog(requireActivity())
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiUpdates.collectLatest { it ->
                    when(it) {
                        is ResponseModel.Loading ->{
                            showDialog()
                        }
                        is ResponseModel.Error ->{
                            Toast.makeText(requireContext(),it.message, Toast.LENGTH_LONG).show()
                        }
                        is ResponseModel.Idle ->{
                            Toast.makeText(requireContext(),"Idle State", Toast.LENGTH_LONG).show()
                        }
                        is ResponseModel.Success ->{
                            if(it.data?.body()?.avatar_thumb.isNullOrEmpty()){
                                dismissDialog()
                                findNavController().navigateUp()
                                Toast.makeText(requireContext(),"No data found", Toast.LENGTH_LONG).show()
                            }else{
                                it.data?.body()?.let{ VideoInf ->
                                    Glide.with(view).load(VideoInf.cover[0]).into(binding.imageView)
                                    dismissDialog()
                                    binding.imageView.visibility = View.VISIBLE
                                    videoInf = VideoInf
                                }
                            }
                        }
                    }
                }

            }
        }
        binding.apply {
            shareBackIcon.setOnClickListener {
                findNavController().navigateUp()
            }
        }
        binding.apply {
            nextButton.setOnClickListener {
                isThereData()
                if(videoInf != null){
                    val id = 0
                    val imageUrl = videoInf!!.cover[0]
                    val videoUrl = videoInf!!.video[0]
                    VideoResponse(id,imageUrl,videoUrl).also { v->
                        viewModel.upsertVideo(v)
                    }
                }
                findNavController().navigate(R.id.action_shareFragment_to_repostListFragment)
            }
        }

    }
    private fun showDialog() {
        viewModel.viewModelScope.launch {
            withContext(Dispatchers.Main){
                binding.imageView.visibility = View.INVISIBLE
                loadingDialog.startLoading()
            }
        }
    }
    private fun dismissDialog(){
        viewModel.viewModelScope.launch {
            withContext(Dispatchers.Main){
                loadingDialog.isDismiss()
            }
        }
    }

    private fun isThereData() {
        val sharedPref =requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("add",true)
        editor.apply()
    }

}

