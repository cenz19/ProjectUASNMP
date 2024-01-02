package com.nmpubaya.cerbung

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.nmpubaya.cerbung.databinding.FragmentCreateBinding

private const val ARG_ID = "id"

class CreateFragment : Fragment() {
    private lateinit var binding: FragmentCreateBinding
    private var id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt(ARG_ID)
        }
    }

    override fun onResume() {
        super.onResume()
        arguments?.let {
            id = it.getInt(ARG_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCreateNewCerbung.setOnClickListener {
            val intent = Intent(activity, CreateCerbung1Activity::class.java)
            intent.putExtra(KEY_ID, id)
            startActivity(intent)
        }
    }

    companion object {
         val KEY_ID = "id"
        @JvmStatic
        fun newInstance(id: Int) =
            CreateFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ID, id)
                }
            }
    }
}