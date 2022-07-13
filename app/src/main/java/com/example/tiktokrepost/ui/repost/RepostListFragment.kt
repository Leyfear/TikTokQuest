package com.example.tiktokrepost.ui.repost

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tiktokrepost.R
import com.example.tiktokrepost.adapter.VideoAdapter
import com.example.tiktokrepost.databinding.FragmentRepostListBinding
import com.example.tiktokrepost.db.VideoResponse
import com.example.tiktokrepost.network.URL
import com.example.tiktokrepost.uitel.SheetDialog
import com.example.tiktokrepost.viewmodel.ReposViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class RepostListFragment : Fragment() {
    private lateinit var binding : FragmentRepostListBinding
    private lateinit var sheedDialog: SheetDialog
    var onItemClickDelAll: ((List<VideoResponse>) -> Unit)?= null
    private var list : List<VideoResponse>? = null
    private lateinit var next : TextView
    private lateinit var button: ImageView
    private lateinit var linkText: EditText
    private lateinit var videoAdapter: VideoAdapter
    private val viewModel: ReposViewModel by sharedViewModel()
    private lateinit var postArrayList : ArrayList<VideoResponse>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postArrayList = ArrayList<VideoResponse>()
        sheedDialog = SheetDialog(requireContext(),requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRepostListBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postArrayList.clear()
        sheedDialog.setDialog()
        setAdapter()
        viewFromDialog()
        nextHandle()
        binding.apply {
            settingsIcon.setOnClickListener {
                findNavController().navigate(R.id.action_repostListFragment_to_settingsFragment)
            }
        }
        binding.apply {
            btnAddNote.setOnClickListener {
                sheedDialog.showDialog()
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.videos.collectLatest{
                        videoAdapter.differ.submitList(it)
                        list = it
                    }
                }
            }
        }
        binding.apply {
            deleteIcon.setOnClickListener {
                if(list?.size != 0){
                    val currentlist =videoAdapter.differ.currentList
                    onItemClickDelAll?.invoke(currentlist)
                }
                if(list?.size == postArrayList.size ){
                    isThereData()
                    findNavController().navigate(R.id.action_repostListFragment_to_repostFragment)
                }
                if(postArrayList.isNotEmpty()){
                    for (model in postArrayList){
                        viewModel.deleteVideo(model)
                    }
                    postArrayList.clear()
                }

            }
        }

        videoAdapter.onItemLongClickAdd = {
            println(it.videoId)
            postArrayList.add(it)
        }
        videoAdapter.onItemClick = {
            val bundle = Bundle().apply {
                putParcelable("video",it)
            }
            findNavController().navigate(R.id.action_repostListFragment_to_videoViewFragment,bundle)
        }


        videoAdapter.onItemLongClickDelete = {
            println(it.videoId)
            postArrayList.remove(it)
        }


    }

    private fun viewFromDialog() {
        next = sheedDialog.next
        button = sheedDialog.button
        linkText = sheedDialog.linkText
    }


    private fun nextHandle() {

        next.setOnClickListener {
            val tiktokLink = linkText.text.toString()
            if(tiktokLink.isNotEmpty()){
                viewModel.viewModelScope.launch {
                    //link = "https://www.tiktok.com/@nor10122/video/7037155617491913986"
                    viewModel.getVideo(tiktokLink, URL.API_KEY, URL.HOST_KEY)
                }
                findNavController().navigate(R.id.action_repostListFragment_to_shareFragment)
                sheedDialog.dissmissDialog()
            }else{
                Toast.makeText(requireContext(),"Url is required.",Toast.LENGTH_LONG).show()
            }

        }
        button.setOnClickListener {
            sheedDialog.dissmissDialog()
            linkText.setText("")
        }
    }

    private fun setAdapter() {
        val decor = ItemRowDecoration(2,25,true)
        videoAdapter = VideoAdapter()
        binding.recView.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = videoAdapter
            addItemDecoration(decor)
        }
    }
    private fun isThereData() {
        val sharedPref =requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("add",false)
        editor.apply()
    }

}