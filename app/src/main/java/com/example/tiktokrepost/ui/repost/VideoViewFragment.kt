package com.example.tiktokrepost.ui.repost

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import androidx.navigation.fragment.navArgs
import com.example.tiktokrepost.R
import com.example.tiktokrepost.databinding.FragmentVideoViewBinding


class VideoViewFragment : Fragment() {
    private lateinit var binding : FragmentVideoViewBinding
    private var videoUrl : String? = null
    val args by navArgs<VideoViewFragmentArgs>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding = FragmentVideoViewBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val videoView = binding.videoView
        args.video?.let {
            videoUrl = it.videoUrl
        }
        videoView.setVideoPath(videoUrl)
        val mediaController = MediaController(requireContext())
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)
        videoView.start()
    }
}