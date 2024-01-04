package com.nmpubaya.cerbung

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.nmpubaya.cerbung.databinding.FragmentMostLikedBinding


class MostLikedFragment : Fragment() {
    private lateinit var binding: FragmentMostLikedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_most_liked, container, false)
        binding = FragmentMostLikedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Users"
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MostLikedFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}