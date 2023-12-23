package com.nmpubaya.cerbung

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast


private const val ARG_CERBUNG_ID = "cerbung_id"



class ReadPublicFragment : Fragment() {
    private var cerbung_id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cerbung_id = it.getInt(ARG_CERBUNG_ID)
            Toast.makeText(context, "Cerbung ID: $cerbung_id", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_read_public, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(cerbung_id: Int) =
            ReadPublicFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_CERBUNG_ID, cerbung_id)
                }
            }
    }
}