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
import com.nmpubaya.cerbung.databinding.FragmentReadPublicBinding
import com.squareup.picasso.Picasso
import org.json.JSONObject


private const val ARG_CERBUNG = "cerbung"
private const val ARG_ID = "id"



class ReadPublicFragment : Fragment() {
    private var cerbung: Cerbung? = null
    private lateinit var binding: FragmentReadPublicBinding
    private var users_id: Int? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cerbung = it.getParcelable(ARG_CERBUNG)
            users_id = it.getInt(ARG_ID)
        }
    }

    override fun onResume() {
        super.onResume()
        arguments?.let {
            cerbung = it.getParcelable(ARG_CERBUNG)
            users_id = it.getInt(ARG_ID)
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

    fun followCerbung(cerbung_id: Int, users_id: Int) {
        val q = Volley.newRequestQueue(activity)
        val url = "https://ubaya.me/native/160421005/create_follow_cerbung.php"
        val dialog = AlertDialog.Builder(activity)
        val stringRequest = object : StringRequest(Request.Method.POST, url,
            {
                Log.d("apiresult", it)
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK") {
                    dialog.setMessage("Successfully add cerbung to follow")
                    dialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                        dialog.dismiss()
                    })
                    dialog.create().show()
                } else if (obj.getString("result") == "ERROR") {
                    if (obj.getString("msg") == "Data already exists") {
                        dialog.setMessage("Cerbung is already followed by you")
                        dialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                            dialog.dismiss()
                        })
                        dialog.create().show()
                    }
                }
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
                params["is_follow"] = "1"
                return params
            }
        }
        q.add(stringRequest)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var url = cerbung?.url_gambar
        var builder = Picasso.Builder(view.context)
        builder.listener { picasso, uri, exception ->  exception.printStackTrace()}
        Picasso.get().load(url).into(binding.imgCerbung)
        binding.txtJudul.text = cerbung?.title
        binding.txtListCount.text = cerbung?.num_paragraph.toString()
        binding.txtLikeCount.text = cerbung?.num_likes.toString()
        binding.txtCreator.text = cerbung?.username
        binding.txtDateCreated.text = cerbung?.waktu_dibuat
        binding.txtGenre.text = cerbung?.genre

        binding.btnFollow.setOnClickListener {
            val cerbung_id = cerbung?.id
            val user_id = users_id
//            Toast.makeText(activity, "Cer ID: $cerbung_id, User: $user_id", Toast.LENGTH_LONG).show()
            followCerbung(cerbung_id!!, user_id!!)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(cerbung: Cerbung, users_id: Int) =
            ReadPublicFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_CERBUNG, cerbung)
                    putInt(ARG_ID, users_id)
                }
            }
    }
}