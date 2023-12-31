package com.nmpubaya.cerbung

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.nmpubaya.cerbung.databinding.FragmentReadPublicBinding
import com.squareup.picasso.Picasso


private const val ARG_CERBUNG = "cerbung"



class ReadPublicFragment : Fragment() {
    private var cerbung: Cerbung? = null
    private lateinit var binding: FragmentReadPublicBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cerbung = it.getParcelable(ARG_CERBUNG)
        }
    }

    override fun onResume() {
        super.onResume()
        arguments?.let {
            cerbung = it.getParcelable(ARG_CERBUNG)
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

        with (binding) {
            var url = cerbung?.url_gambar
            var builder = Picasso.Builder(view.context)
            builder.listener { picasso, uri, exception ->  exception.printStackTrace()}
            Picasso.get().load(url).into(imgCerbung)
            txtJudul.text = cerbung?.title
            txtListCount.text = cerbung?.num_paragraph.toString()
            txtLikeCount.text  = cerbung?.num_likes.toString()
            txtCreator.text = cerbung?.username
            txtDateCreated.text = cerbung?.waktu_dibuat
            txtGenre.text = cerbung?.genre

        }
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