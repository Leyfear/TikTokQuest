package com.example.tiktokrepost.ui.repost



import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.tiktokrepost.R
import com.example.tiktokrepost.databinding.FragmentRepostBinding
import com.example.tiktokrepost.network.URL
import com.example.tiktokrepost.uitel.SheetDialog
import com.example.tiktokrepost.viewmodel.ReposViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class RepostFragment : Fragment(R.layout.fragment_repost) {
    private lateinit var next : TextView
    private lateinit var button: ImageView
    private lateinit var linkText: EditText
    private lateinit var sheedDialog: SheetDialog
    private val viewModel: ReposViewModel by sharedViewModel()
    private lateinit var binding : FragmentRepostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sheedDialog = SheetDialog(requireContext(),requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRepostBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(isThereData()){
            findNavController().navigate(R.id.action_repostFragment_to_repostListFragment)
        }
        sheedDialog.setDialog()
        viewFromDialog()
        nextHandle()
        binding.btnAddNote.setOnClickListener {
            sheedDialog.showDialog()
        }
        binding.settingsIcon.setOnClickListener {
            findNavController().navigate(R.id.action_repostFragment_to_settingsFragment)
        }
    }

    private fun isThereData(): Boolean {
        val sharedPref = requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("add",false)
    }

    private fun nextHandle() {
        next.setOnClickListener {
            val tiktokLink = linkText.text.toString()
            if(tiktokLink.isNotEmpty()){
                viewModel.viewModelScope.launch {
                    //val link = "https://www.tiktok.com/@nor10122/video/7037155617491913986"
                    viewModel.getVideo(tiktokLink, URL.API_KEY, URL.HOST_KEY)
                }
                findNavController().navigate(R.id.action_repostFragment_to_shareFragment)
                sheedDialog.dissmissDialog()
            }else{
                Toast.makeText(requireContext(),"Url is required.", Toast.LENGTH_LONG).show()
            }
        }
        button.setOnClickListener {
            sheedDialog.dissmissDialog()
            linkText.setText("")
        }
    }
    private fun viewFromDialog() {
        next = sheedDialog.next
        button = sheedDialog.button
        linkText = sheedDialog.linkText
    }

}