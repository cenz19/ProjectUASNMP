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
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nmpubaya.cerbung.databinding.FragmentReadRestrictedBinding
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar


private const val ARG_CERBUNG = "cerbung"
private const val ARG_ID = "id"


class ReadRestrictedFragment : Fragment() {
    private var cerbung: Cerbung? = null
    var paragraphs: ArrayList<Paragraph> = arrayListOf()
    private lateinit var binding: FragmentReadRestrictedBinding
    private var users_id: Int? =null

    fun updateParagraph() {
        val lm = LinearLayoutManager(activity)
        with(binding.paragraphRecView) {
            layoutManager = lm
            setHasFixedSize(true)
            adapter = ParagraphAdapter(paragraphs)
        } }

    fun refresh() {
        val q = Volley.newRequestQueue(activity)
        val url = "https://ubaya.me/native/160421005/get_all_paragraphs.php"
        val stringRequest = object: StringRequest(Request.Method.POST, url,
            {
                Log.d("apiresult", it.toString())
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")
                    val sType = object : TypeToken<List<Paragraph>>() { }.type
                    paragraphs = Gson().fromJson(data.toString(), sType) as ArrayList<Paragraph>
                    updateParagraph()
                }
            },
            {
                Log.e("apiresult", it.message.toString())
            }) {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["cerbung_id"] = cerbung?.id.toString()
                return params
            }
        }
        q.add(stringRequest)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cerbung = it.getParcelable(ARG_CERBUNG)
            users_id = it.getInt(ARG_ID)
        }
    }

    override fun onResume() {
        super.onResume()
        refresh()
        arguments?.let {
            cerbung = it.getParcelable(ARG_CERBUNG)
            users_id = it.getInt(ARG_ID)
        }
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = "Read Cerbung"
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
    fun getAccess(cerbung_id: Int, users_id: Int) {
        val q = Volley.newRequestQueue(activity)
        val url = "https://ubaya.me/native/160421005/create_access.php"
        val dialog = AlertDialog.Builder(activity)
        val stringRequest = object : StringRequest(Request.Method.POST, url,
            {
                Log.d("apiresult", it)
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK") {
                    dialog.setMessage("Access request is sent to the Cerbung maker")
                    dialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                        dialog.dismiss()
                    })
                    dialog.create().show()
                } else if (obj.getString("result") == "ERROR") {
                    if (obj.getString("msg") == "Data already exists") {
                        dialog.setMessage("You already sent the request to contribute to Cerbung maker")
                        dialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                            dialog.dismiss()
                        })
                        dialog.create().show()
                    }
                }
            },
            {
                Log.e("apiresult", it.message.toString())
                dialog.setMessage("Failed to create request to Cerbung maker")
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
                params["notifications_id"] = "1"
                params["status_approval"] = "0"
                val date = Calendar.getInstance().time
                val formatter = SimpleDateFormat("yyyy-MM-dd")
                val waktu_notif = formatter.format(date)
                params["waktu_notif"] = waktu_notif
                return params
            }
        }
        q.add(stringRequest)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = "Read Cerbung"
        val url = cerbung?.url_gambar
        val builder = Picasso.Builder(view.context)
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

        binding.btnSubmit.setOnClickListener {
            val cerbung_id = cerbung?.id
            val user_id = users_id
            getAccess(cerbung_id!!, user_id!!)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(cerbung : Cerbung, users_id: Int) =
            ReadRestrictedFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_CERBUNG, cerbung)
                    putInt(ARG_ID, users_id)
                }
            }
    }
}