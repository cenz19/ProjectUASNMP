package com.nmpubaya.cerbung

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.nmpubaya.cerbung.databinding.FragmentReadRestrictedBinding
import com.squareup.picasso.Picasso
import org.json.JSONObject


private const val ARG_CERBUNG = "cerbung"


class ReadRestrictedFragment : Fragment() {
    private var cerbung: Cerbung? = null
    private lateinit var binding: FragmentReadRestrictedBinding

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
        //return inflater.inflate(R.layout.fragment_read_restricted, container, false)
        binding = FragmentReadRestrictedBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun followCerbung(cerbung_id: Int, users_id: Int, is_follow: Int) {
        val q = Volley.newRequestQueue(activity)
        val url = "https://ubaya.me/native/160421005/create_follow_cerbung.php"
        val dialog = AlertDialog.Builder(activity)
        val stringRequest = object : StringRequest(Request.Method.POST, url,
            {
                Log.d("apiresult", it)
                dialog.setMessage("Successfully add cerbung to follow")
                dialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
                dialog.create().show()
            },
            {
                Log.e("apiresult", it.message.toString())
                dialog.setMessage("Failed to add cerbung to follow")
                dialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
                dialog.create().show()
            }
        ) {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["cerbung_id"] = cerbung_id.toString()
                params["users_id"] = users_id.toString()
                params["is_follow"] = is_follow.toString()
                return params
            }
        }
        q.add(stringRequest)
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

            btnFollow.setOnClickListener {
                followCerbung(cerbung!!.id, cerbung!!.users_id, 1)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(cerbung : Cerbung) =
            ReadRestrictedFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_CERBUNG, cerbung)
                }
            }
    }
}