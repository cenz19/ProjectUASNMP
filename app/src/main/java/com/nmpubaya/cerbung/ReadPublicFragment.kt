package com.nmpubaya.cerbung

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.nmpubaya.cerbung.databinding.FragmentReadPublicBinding


private const val ARG_CERBUNG = "cerbung_id"



class ReadPublicFragment : Fragment() {
    private var cerbung: Cerbung? = null
    private lateinit var binding: FragmentReadPublicBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cerbung = it.getParcelable(ARG_CERBUNG)
            Toast.makeText(context, "Cerbung ID: ${cerbung!!.id}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_read_public, container, false)
        binding = FragmentReadPublicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newInstance(cerbung: Cerbung) =
            ReadPublicFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_CERBUNG, cerbung)
                }
            }
    }
}